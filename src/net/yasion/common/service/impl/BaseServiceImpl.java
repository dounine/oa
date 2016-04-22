package net.yasion.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.uurp.UurpAdaptor;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.BaseModel;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IBaseService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.service.enumeration.OperatorType;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.UserUtils;
import net.yasion.common.web.tag.PermissionELFunction;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseServiceImpl<POJO extends BaseModel<ID>, DTO extends BaseDTO<ID>, ID extends Serializable> implements IBaseService<POJO, DTO, ID> {

	protected DateFormat dateFormat = new SimpleDateFormat(CommonConstants.COMMON_DATA_TIME_FORMAT);

	/**
	 * 根据DTO，得到以DTO的OperateRelation为基础的属性Criterion列表(Criterion是查询条件)
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回属性Criterion列表
	 */
	protected final List<Criterion> getPropertyCriterionByDTO(BaseDTO<? extends Serializable> dto) {
		Map<String, List<Criterion>> criterionListMap = new HashMap<String, List<Criterion>>();
		Map<String, Criterion> criterionMap = new HashMap<String, Criterion>();
		List<Criterion> criterionList = (null != dto.getDefCriterion() ? new ArrayList<Criterion>(Arrays.asList(new Criterion[] { dto.getDefCriterion() })) : null);
		if (null == criterionList || 0 == criterionList.size()) {
			Map<String, List<OperatorType>> operMap = dto.getOperateRelation();
			boolean isModelSearch = (null != dto.getOperateValue() && 0 < dto.getOperateValue().size());
			Field[] dtoFields = AfxBeanUtils.getAllFields(dto.getClass());
			for (int i = 0; i < dtoFields.length; i++) {
				String dtoFieldName = dtoFields[i].getName();
				Object dtoOrigValue = (isModelSearch ? dto.getOperateValue().get(dtoFieldName) : AfxBeanUtils.getFieldValue(dto, dtoFieldName));
				dtoOrigValue = (null == dtoOrigValue ? (dto.isModelDTOSearchOnField() ? AfxBeanUtils.getFieldValue(dto, dtoFieldName) : null) : dtoOrigValue);
				if (!dto.isDefExcludedProperty(dtoFieldName, dtoOrigValue) && !dto.isExcludedProperty(dtoFieldName, dtoOrigValue)) {
					String expDTOFieldName = (StringUtils.isBlank(dto.getCriterionAlias()) ? dtoFieldName : dto.getCriterionAlias() + "." + dtoFieldName);// 如果有使用别名则加入别名,expDTOFieldName跟dtoFieldName区分开是为了影响其他地方对dtoFieldName的Map取值
					List<?> valList = (null == dtoOrigValue ? new ArrayList<Object>() : (dtoOrigValue.getClass().isArray() ? Arrays.asList((Object[]) dtoOrigValue) : (dtoOrigValue instanceof Collection ? new ArrayList<Object>((Collection<?>) dtoOrigValue) : Arrays
							.asList(new Object[] { dtoOrigValue }))));// 原始值
					List<OperatorType> operTypeList = operMap.get(dtoFieldName);
					if (CollectionUtils.isEmpty(operTypeList)) {// 默认EQ
						OperatorType operType = OperatorType.EQ;
						if (1 < valList.size()) {
							List<Criterion> generatedCriterionList = new ArrayList<Criterion>();
							for (int j = 0, len = valList.size(); j < len; j++) {
								Criterion generatedCriterion = generateCriterion(expDTOFieldName, operType, valList.get(j));
								if (null != generatedCriterion) {
									generatedCriterionList.add(generatedCriterion);
								}
							}
							if (CollectionUtils.isNotEmpty(generatedCriterionList)) {
								criterionMap.put(dtoFieldName, Restrictions.or(generatedCriterionList.toArray(new Criterion[0])));
							}
						} else {
							Criterion generatedCriterion = generateCriterion(expDTOFieldName, operType, (0 < valList.size() ? valList.get(0) : null));
							if (null != generatedCriterion) {
								criterionMap.put(dtoFieldName, generatedCriterion);
							}
						}
					} else if (1 == operTypeList.size()) {// 使用第一个值
						OperatorType operType = operTypeList.get(0);
						Criterion generatedCriterion = null;
						switch (operType) {
						case BETWEEN:
						case NOTBETWEEN:
						case IN:
						case NOTIN:
							generatedCriterion = generateCriterion(expDTOFieldName, operType, valList);
							break;
						case ISNULL:
						case ISNOTNULL:
						case ISEMPTY:
						case ISNOTEMPTY:
							List<Criterion> blankGeneratedNotBlankCriterionList = new ArrayList<Criterion>();
							for (int j = 0, len = valList.size(); j < len; j++) {
								Criterion blankGeneratedNotBlankCriterion = generateCriterion(expDTOFieldName, OperatorType.EQ, valList.get(j));
								if (null != blankGeneratedNotBlankCriterion) {
									blankGeneratedNotBlankCriterionList.add(blankGeneratedNotBlankCriterion);
								}
							}
							Criterion blankGeneratedCriterion = generateCriterion(expDTOFieldName, operType, null);
							if (null != blankGeneratedCriterion) {
								blankGeneratedNotBlankCriterionList.add(blankGeneratedCriterion);
							}
							if (CollectionUtils.isNotEmpty(blankGeneratedNotBlankCriterionList)) {
								generatedCriterion = Restrictions.or(blankGeneratedNotBlankCriterionList.toArray(new Criterion[0]));
							}
							break;
						default:
							generatedCriterion = generateCriterion(expDTOFieldName, operType, (0 < valList.size() ? valList.get(0) : null));
							break;
						}
						if (null != generatedCriterion) {
							criterionMap.put(dtoFieldName, generatedCriterion);
						}
					} else {
						List<Criterion> generatedCriterionList = new ArrayList<Criterion>();
						int valSize = valList.size();
						int valIndex = 0, index = 0;
						for (int j = 0, len = operTypeList.size(); j < len; j++, valIndex++) {
							boolean isIN = false;
							OperatorType operType = operTypeList.get(j);
							Object operVal = null;
							switch (operType) {
							case BETWEEN:
							case NOTBETWEEN:
								index = (valSize < (valIndex + 2) ? valSize : valIndex + 2);// 使用两个元素
								operVal = (valSize > valIndex ? valList.subList(valIndex, index) : null);
								valIndex = index - 1;// 要后退一位,因为subList的第二个参数是不包含的
								break;
							case IN:
							case NOTIN:
								valList = (valSize > valIndex ? valList.subList(valIndex, valSize) : null);
								operVal = valList;
								valIndex = valSize;// 设置为最后一个元素
								isIN = true;
								break;
							case ISNULL:
							case ISNOTNULL:
							case ISEMPTY:
							case ISNOTEMPTY:
								operVal = null;// 不需要值
								valIndex--;// 因为ISNULL、ISNOTNUL、ISEMPTY、ISNOTEMPTY,不消耗valList的值,保持那个位置不动
								break;
							default:
								operVal = (valSize > valIndex ? valList.get(valIndex) : null);
								break;
							}
							Criterion generatedCriterion = generateCriterion(expDTOFieldName, operType, operVal);
							if (null != generatedCriterion) {
								generatedCriterionList.add(generatedCriterion);
							}
							if (isIN) {
								break;
							}
						}
						if (valSize > operTypeList.size() && valSize > valIndex) {// 还有val未被使用
							for (int j = valIndex; j < valSize; j++, valIndex++) {
								Object operVal = valList.get(valIndex);
								Criterion generatedCriterion = generateCriterion(expDTOFieldName, OperatorType.EQ, operVal);
								if (null != generatedCriterion) {
									generatedCriterionList.add(generatedCriterion);
								}
							}
						}
						if (CollectionUtils.isNotEmpty(generatedCriterionList)) {
							criterionListMap.put(dtoFieldName, generatedCriterionList);
						}
					}
				}
			}
			boolean useGeneratedCriterion = false;
			if (StringUtils.isNotBlank(dto.getCriteriaLogicExpression())) {
				Criterion newCriterion = this.processCriteriaExpression(criterionMap, criterionListMap, dto.getCriteriaLogicExpression(), dto.isDealRemaind());
				if (null != newCriterion) {
					criterionList = new ArrayList<Criterion>(Arrays.asList(new Criterion[] { newCriterion }));
				} else {
					useGeneratedCriterion = true;
					// criterionList = new ArrayList<Criterion>(criterionMap.values());
				}
			}
			if (StringUtils.isBlank(dto.getCriteriaLogicExpression()) || useGeneratedCriterion) {
				if (MapUtils.isNotEmpty(criterionListMap)) {// 如果没有表达式控制,关系直接用OR来确定
					Set<Entry<String, List<Criterion>>> criterionListSet = criterionListMap.entrySet();
					Iterator<Entry<String, List<Criterion>>> criterionListEntryIt = criterionListSet.iterator();
					while (criterionListEntryIt.hasNext()) {
						Entry<String, List<Criterion>> criterionListEntry = criterionListEntryIt.next();
						List<Criterion> generatedCriterionList = criterionListEntry.getValue();
						if (CollectionUtils.isNotEmpty(generatedCriterionList)) {
							Criterion orCriterion = Restrictions.or(generatedCriterionList.toArray(new Criterion[0]));
							criterionMap.put(criterionListEntry.getKey(), orCriterion);
						}
					}
				}
				criterionList = new ArrayList<Criterion>(criterionMap.values());
			}
		}
		return criterionList;
	}

	/**
	 * 用字段名、操作类型、操作值生成对用的Criterion对象
	 * 
	 * @param expressionFieldName
	 *            表达式内的字段名,可以包含实体的别名(例:user.username)
	 * @param operType
	 *            操作类型,就是等于、不等于、大于、小于等等
	 * @param value
	 *            操作的值
	 * @return 返回生成好的Criterion对象
	 */
	private Criterion generateCriterion(String expressionFieldName, OperatorType operType, Object value) {
		Criterion criterion = null;
		if (null != value) {// 值不为空的生成正常值
			operType = (null != operType ? operType : OperatorType.EQ);
			switch (operType) {// 匹配对操作
			case EQ:
			case NOTEQ:
				Criterion eqCriterion = Restrictions.eq(expressionFieldName, value);
				if (OperatorType.NOTEQ.equals(operType)) {
					eqCriterion = Restrictions.not(eqCriterion);
				}
				criterion = eqCriterion;
				break;
			case GT:
				criterion = Restrictions.gt(expressionFieldName, value);
				break;
			case LIKE:
			case FULLLIKE:
			case STARTLIKE:
			case ENDLIKE:
			case NOTLIKE:
			case NOTFULLLIKE:
			case NOTSTARTLIKE:
			case NOTENDLIKE:
				Criterion likeCriterion = null;
				switch (operType) {
				case LIKE:
				case NOTLIKE:
					// 字符串在中间匹配.相当于"like '%value%'"
					likeCriterion = Restrictions.like(expressionFieldName, value.toString(), MatchMode.ANYWHERE);
					break;
				case FULLLIKE:
				case NOTFULLLIKE:
					// 字符串精确匹配.相当于"like 'value'"
					likeCriterion = Restrictions.like(expressionFieldName, value.toString(), MatchMode.EXACT);
					break;
				case STARTLIKE:
				case NOTSTARTLIKE:
					// 字符串在最前面的位置.相当于"like 'value%'"
					likeCriterion = Restrictions.like(expressionFieldName, value.toString(), MatchMode.START);
					break;
				case ENDLIKE:
				case NOTENDLIKE:
					// 字符串在最后面的位置.相当于"like '%value'"
					likeCriterion = Restrictions.like(expressionFieldName, value.toString(), MatchMode.END);
					break;
				default:// 其他操作不处理
					break;
				}
				if (null != likeCriterion) {// 必须是有效的匹配
					if (OperatorType.NOTLIKE.equals(operType) || OperatorType.NOTFULLLIKE.equals(operType) || OperatorType.NOTSTARTLIKE.equals(operType) || OperatorType.NOTENDLIKE.equals(operType)) {// 如果是有not的，加入not表达式
						likeCriterion = Restrictions.not(likeCriterion);
					}
					criterion = likeCriterion;
				}
				break;
			case ILIKE:
			case IFULLLIKE:
			case ISTARTLIKE:
			case IENDLIKE:
			case INOTLIKE:
			case INOTFULLLIKE:
			case INOTSTARTLIKE:
			case INOTENDLIKE:
				Criterion iLikeCriterion = null;
				switch (operType) {
				case ILIKE:
				case INOTLIKE:
					// 字符串在中间匹配.相当于"like '%value%' (忽略大小写)"
					iLikeCriterion = Restrictions.ilike(expressionFieldName, value.toString(), MatchMode.ANYWHERE);
					break;
				case IFULLLIKE:
				case INOTFULLLIKE:
					// 字符串精确匹配.相当于"like 'value' (忽略大小写)"
					iLikeCriterion = Restrictions.ilike(expressionFieldName, value.toString(), MatchMode.EXACT);
					break;
				case ISTARTLIKE:
				case INOTSTARTLIKE:
					// 字符串在最前面的位置.相当于"like 'value%' (忽略大小写)"
					iLikeCriterion = Restrictions.ilike(expressionFieldName, value.toString(), MatchMode.START);// 字符串在最前面的位置.相当于"like 'value%'"
					break;
				case IENDLIKE:
				case INOTENDLIKE:
					// 字符串在最后面的位置.相当于"like '%value' (忽略大小写)"
					iLikeCriterion = Restrictions.ilike(expressionFieldName, value.toString(), MatchMode.END);
					break;
				default:// 其他操作不处理
					break;
				}
				if (null != iLikeCriterion) {// 必须是有效的匹配
					if (OperatorType.INOTLIKE.equals(operType) || OperatorType.INOTFULLLIKE.equals(operType) || OperatorType.INOTSTARTLIKE.equals(operType) || OperatorType.INOTENDLIKE.equals(operType)) {// 如果是有not的，加入not表达式
						iLikeCriterion = Restrictions.not(iLikeCriterion);
					}
					criterion = iLikeCriterion;
				}
				break;
			case LT:
				criterion = Restrictions.lt(expressionFieldName, value);
				break;
			case IN:
			case NOTIN:
				Criterion inCriterion = null;
				if (value instanceof Collection) {
					inCriterion = Restrictions.in(expressionFieldName, (Collection<?>) value);
				} else if (value.getClass().isArray()) {
					inCriterion = Restrictions.in(expressionFieldName, (Object[]) AfxBeanUtils.getWrapperObject(value));
				} else {// 如果都不是，转为eq
					inCriterion = Restrictions.eq(expressionFieldName, value);
				}
				if (null != inCriterion) {// 必须是有效的匹配
					if (OperatorType.NOTIN.equals(operType)) {
						inCriterion = Restrictions.not(inCriterion);
					}
					criterion = inCriterion;
				}
				break;
			case NE:
				criterion = Restrictions.ne(expressionFieldName, value);
				break;
			case GE:
				criterion = Restrictions.ge(expressionFieldName, value);
				break;
			case LE:
				criterion = Restrictions.le(expressionFieldName, value);
				break;
			case BETWEEN:
			case NOTBETWEEN:
				Criterion betweenCriterion = null;
				if (value instanceof Collection) {
					Collection<?> dtoCollValue = (Collection<?>) value;
					if (2 <= dtoCollValue.size()) {
						Iterator<?> dtoIt = dtoCollValue.iterator();
						Object firstValue = dtoIt.next();
						Object secondValue = dtoIt.next();
						betweenCriterion = Restrictions.between(expressionFieldName, firstValue, secondValue);
					} else {
						if (1 == dtoCollValue.size()) {
							Iterator<?> dtoIt = dtoCollValue.iterator();
							Object firstValue = dtoIt.next();
							betweenCriterion = Restrictions.eq(expressionFieldName, firstValue);
						}
					}
				} else if (value.getClass().isArray()) {
					Object[] dtoArrValue = (Object[]) AfxBeanUtils.getWrapperObject(value);
					if (2 <= dtoArrValue.length) {
						betweenCriterion = Restrictions.between(expressionFieldName, dtoArrValue[0], dtoArrValue[1]);
					} else {
						if (1 == dtoArrValue.length) {
							betweenCriterion = Restrictions.eq(expressionFieldName, dtoArrValue[0]);
						}
					}
				} else {// 如果都不是，转为eq
					betweenCriterion = Restrictions.eq(expressionFieldName, value);
				}
				if (null != betweenCriterion) {// 必须是有效的匹配
					if (OperatorType.NOTBETWEEN.equals(operType)) {
						betweenCriterion = Restrictions.not(betweenCriterion);
					}
					criterion = betweenCriterion;
				}
				break;
			default:
				criterion = Restrictions.eq(expressionFieldName, value);
				break;
			}
		} else {// 可能需要生成isNull这些
			if (null != operType) {// 如果有指定操作类型，可能要生成值；如果为空的不生成任何查询
				switch (operType) {
				case ISEMPTY:
					criterion = Restrictions.isEmpty(expressionFieldName);
					break;
				case ISNOTEMPTY:
					criterion = Restrictions.isNotEmpty(expressionFieldName);
					break;
				case ISNULL:
					criterion = Restrictions.isNull(expressionFieldName);
					break;
				case ISNOTNULL:
					criterion = Restrictions.isNotNull(expressionFieldName);
					break;
				default:
					break;
				}
				// 其他操作类型什么也不干，这里只处理isNull、isNotNull
			}
		}
		return criterion;
	}

	/**
	 * 用来处理查询对象复杂的And/Or关系的函数,通过DTO指定的And/Or表达式来计算
	 * 
	 * @param criterionMap
	 *            查询条件对象map,作为每个字段的基本查询条件对象
	 * @param criterionListMap
	 *            查询条件对象map,跟上面不同的是这个map里面包含了多个值的查询条件对象,可以通过:字段名[1]方式取得指定字段中指定index的值
	 * @param expression
	 *            And/Or表达式(例如:And(DTO字段名1,Or(And(DTO字段名2,DTO字段名3),DTO字段名4))),如果表达式不正确将不会得到正确的结果
	 * @param dealRemaind
	 *            是否处理criterionMap、criterionListMap中剩余的条件对象
	 * @return 返回处理后的查询条件对象,如果处理过处出错直接返回null
	 */
	private Criterion processCriteriaExpression(Map<String, Criterion> criterionMap, Map<String, List<Criterion>> criterionListMap, String expression, boolean dealRemaind) {
		Criterion criterionExp = null;
		try {
			Map<String, List<Criterion>> newCriterionListMap = new HashMap<String, List<Criterion>>(criterionListMap);
			Map<String, List<Criterion>> newCriterionListMap2 = new HashMap<String, List<Criterion>>(criterionListMap);// 用来统计有那个条件未用上,最终返回前加回去
			Map<String, Criterion> newCriterionMap = new HashMap<String, Criterion>(criterionMap);
			Map<String, Criterion> newCriterionMap2 = new HashMap<String, Criterion>(criterionMap);// 用来统计有那个条件未用上,最终返回前加回去
			Stack<String> functionStack = new Stack<String>();
			Stack<String> parameterStack = new Stack<String>();
			int count = 0;
			for (int i = 0; i < expression.length(); i++) {
				if (this.isValidNameCharacter(expression.charAt(i))) {
					if (i > 0 && (expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == ',')) {// 参数
						StringBuilder parameter = new StringBuilder();
						boolean isFun = false;// 有可能是方法
						do {
							char thisChar = expression.charAt(i);
							if ('(' == thisChar) {// 后面带(的就是方法
								isFun = true;
								break;
							}
							parameter.append(thisChar);
							i++;
						} while (!(expression.charAt(i) == ',' || expression.charAt(i) == ')'));
						if (!isFun) {
							parameterStack.push(parameter.toString());
						} else {// 方法就入方法的stack
							functionStack.push(parameter.toString());
						}
						i--;
					} else {// 方法
						StringBuilder functionName = new StringBuilder();
						do {
							functionName.append(expression.charAt(i));
							i++;
						} while (expression.charAt(i) != '(');
						functionStack.push(functionName.toString());
						i--;
					}
				} else if (expression.charAt(i) == '(' || expression.charAt(i) == ',') {// 参数前的括号
					if (i > 0 && (expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == ',')) {// 这是可能是多余的括号
						functionStack.push("");// 空方法,避免出错;parameterStack不入此次的括号，因为是多余的
					} else {
						parameterStack.push(Character.toString(expression.charAt(i)));
					}
				} else if (expression.charAt(i) == ')') {// 最后的执行操作
					String functionName = functionStack.pop();
					if (StringUtils.isNotBlank(functionName)) {// 有方法名才处理
						List<String> parameterList = new ArrayList<String>();
						// StringBuilder parameterWord = new StringBuilder();
						String result = "null";
						while (!parameterStack.peek().equals("(")) {
							String parameter = parameterStack.pop();
							if (!",".equals(parameter)) {
								parameterList.add(parameter);
							}
						}
						Collections.reverse(parameterList);// 把参数反转,原来的是反转的
						parameterStack.pop();// 去掉用来区分界限的'('
						// String[] parameters = StringUtils.split(parameterWord.toString(), ",");
						StringBuilder newParameterBuilder = new StringBuilder();
						if (0 < parameterList.size()) {// 有参数
							List<Criterion> criterionList = new ArrayList<Criterion>();
							for (int j = 0, len = parameterList.size(); j < len; j++) {
								String parameter = StringUtils.trim(parameterList.get(j));
								// 新增index参数判断 START
								IndexParameter indexParameter = getIndexParameter(parameter);
								Criterion criterion = null;
								boolean useNewCriterionListMap = false;
								if (null == indexParameter) {
									criterion = newCriterionMap.get(parameter);
									if (null != criterion) {
										if (dealRemaind) {// 记录下来,处理剩余的对象
											newCriterionMap2.remove(parameter);// 用来查找未使用的元素
										}
									} else {
										useNewCriterionListMap = true;// 尝试在newCriterionListMap中查找
									}
								}
								if (null != indexParameter || useNewCriterionListMap) {
									if (useNewCriterionListMap) {// 是尝试在newCriterionListMap中查找的行为
										indexParameter = getIndexParameter(parameter + "[0]");
									}
									Integer parameterIndex = indexParameter.getIndex();
									List<Criterion> parameterCriterionList = newCriterionListMap.get(indexParameter.getParameterName());
									if (CollectionUtils.isNotEmpty(parameterCriterionList)) {
										if ((0 <= parameterIndex && parameterCriterionList.size() > parameterIndex)) {// 有效范围
											criterion = parameterCriterionList.get(parameterIndex.intValue());
											if (dealRemaind && null != criterion) {// 记录下来,处理剩余的对象
												List<Criterion> parameterCriterionList2 = newCriterionListMap2.get(indexParameter.getParameterName());
												if (parameterCriterionList2 == parameterCriterionList) {
													parameterCriterionList2 = new ArrayList<Criterion>(parameterCriterionList);
													newCriterionListMap2.put(indexParameter.getParameterName(), parameterCriterionList2);
												}
												if (CollectionUtils.isNotEmpty(parameterCriterionList2)) {
													parameterCriterionList2.remove(criterion);// 用来查找未使用的元素
													if (CollectionUtils.isEmpty(parameterCriterionList2)) {// 删除这个元素后,List已经空,删除当前List
														newCriterionListMap2.remove(indexParameter.getParameterName());
													}
												} else {
													newCriterionListMap2.remove(indexParameter.getParameterName());
												}
											}
										}
									} else {
										criterion = newCriterionMap.get(indexParameter.getParameterName());
										if (dealRemaind && null != criterion) {// 记录下来,处理剩余的对象
											newCriterionMap2.remove(indexParameter.getParameterName());// 用来查找未使用的元素
										}
									}
								}
								// 新增index参数判断 END
								if (null != criterion) {
									newParameterBuilder.append(parameter);
									criterionList.add(criterion);
								}
							}
							if (CollectionUtils.isNotEmpty(criterionList)) {// 有相关的操作才设置进去
								newParameterBuilder.append(count);
								Criterion resultCriterion = null;
								if (functionName.equalsIgnoreCase("And")) {
									resultCriterion = And(criterionList.toArray(new Criterion[0]));
								} else if (functionName.equalsIgnoreCase("Or")) {
									resultCriterion = Or(criterionList.toArray(new Criterion[0]));
								}
								String newParameter = newParameterBuilder.toString();
								newCriterionMap.put(newParameter, resultCriterion);// 操作之后的结果放回去,后面的数据可以继续处理
								result = newParameter;
							}
						} else {// 忽略
							result = "null";
						}
						parameterStack.push(result);// 操作之后的结果放回去,后面的数据可以继续处理
						count++;
					}
				}
			}
			criterionExp = newCriterionMap.get(parameterStack.pop());
			if (dealRemaind) {
				if (0 < newCriterionMap2.size()) {// 有剩下未处理的查询条件对象，才处理
					List<Criterion> criterionList = new ArrayList<Criterion>(newCriterionMap2.values());
					Criterion andCriterion = Restrictions.and(criterionList.toArray(new Criterion[0]));
					criterionExp = (null != criterionExp) ? Restrictions.and(criterionExp, andCriterion) : andCriterion;
				}
				if (0 < newCriterionListMap2.size()) {
					List<Criterion> newCriterionList = new ArrayList<Criterion>();
					List<List<Criterion>> criterionListList = new ArrayList<List<Criterion>>(newCriterionListMap2.values());
					for (int i = 0, len = criterionListList.size(); i < len; i++) {
						List<Criterion> criterionList = criterionListList.get(i);
						Criterion orCriterion = Restrictions.or(criterionList.toArray(new Criterion[0]));
						newCriterionList.add(orCriterion);
					}
					criterionExp = (null != criterionExp) ? Restrictions.and(criterionExp, Restrictions.and(newCriterionList.toArray(new Criterion[0]))) : Restrictions.and(newCriterionList.toArray(new Criterion[0]));
				}
			}
			return criterionExp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 出错直接返回null
		}
		// 输入对应的函数测试
		// "or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(whiteUrls[1],code[3]))))),descr[0])"
		// "or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(descr[2],code[3]))))),descr[0])"
		// "or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(descr[2],code[3]))))),descr[0],name[2],name[1])"
		// "or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(descr[2],code[3]))))),descr[0],name[2],name[1],name[3])"
		// "or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(descr[2],code[3]))))),descr[0],name[2],name[1],name[3],descr,descr[3])"
	}

	// public static void main(String[] args) {
	// PermissionDTO permissionDTO = new PermissionDTO();
	// permissionDTO.getOperateValue().put("whiteUrls", Arrays.asList(new String[] { "whiteUrls1", "whiteUrls2" }));
	// permissionDTO.getOperateValue().put("descr", Arrays.asList(new String[] { "descr1", "descr2" }));
	// permissionDTO.getOperateValue().put("code", Arrays.asList(new String[] { "code1", "code2", "code3", "code4", "code5", "code6", "code7" }));
	// permissionDTO.getOperateValue().put("name", Arrays.asList(new String[] { "name1", "name2", "name3", "name4", "name5" }));
	// permissionDTO.getOperateRelationMap().put("name", Arrays.asList(new OperatorType[] { OperatorType.LIKE, OperatorType.BETWEEN, OperatorType.BETWEEN }));
	// permissionDTO.getOperateRelationMap().put("code", Arrays.asList(new OperatorType[] { OperatorType.LIKE, OperatorType.BETWEEN, OperatorType.IN, OperatorType.EQ }));
	// permissionDTO.getOperateRelationMap().put("descr", Arrays.asList(new OperatorType[] { OperatorType.LIKE, OperatorType.LIKE, OperatorType.ISNULL, OperatorType.EQ }));
	// permissionDTO.setCriteriaLogicExpression("or(And(descr[1],Or(code[1],And(whiteUrls,or(code[0],code[2],code[1],AND(descr[2],code[3]))))),descr[0],name[2],name[1],name[3],descr,descr[3])");
	// System.out.println(new BaseServiceImpl<BaseModel<Serializable>, BaseDTO<Serializable>, Serializable>() {
	// @Override
	// protected IBaseDAO<BaseModel<Serializable>, Serializable> getDefaultDAO() {
	// return null;
	// }
	// }.getPropertyCriterionByDTO(permissionDTO));
	// }

	/**
	 * 模拟函数and
	 * 
	 * @param criterion
	 *            查询条件对象
	 * @return and操作后的查询条件对象
	 */
	private Criterion And(Criterion... criterion) {
		if (null != criterion && 0 < criterion.length) {
			Criterion and = Restrictions.and(criterion);
			return and;
		}
		return null;
	}

	/**
	 * 模拟函数or
	 * 
	 * @param criterion
	 *            查询条件对象
	 * @return or操作后的查询条件对象
	 */
	private Criterion Or(Criterion... criterion) {
		if (null != criterion && 0 < criterion.length) {
			Criterion or = Restrictions.or(criterion);
			return or;
		}
		return null;
	}

	/**
	 * 是否有效作为参数名或者方法面的字符,为了排除'('、')'作为参数名或方法名
	 * 
	 * @param c
	 *            要检验的字符
	 * @return 是否有效
	 */
	private boolean isValidNameCharacter(Character c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || (' ' == c) || (c == '[' || c == ']');
	}

	private IndexParameter getIndexParameter(String parameter) {
		Pattern pattern = Pattern.compile("^(\\w+)\\[(\\d+)\\]$");
		Matcher matcher = pattern.matcher(parameter);
		if (matcher.matches()) {
			String parameterName = matcher.group(1);// parameterName
			String indexStr = matcher.group(2);// index
			if (NumberUtils.isNumber(indexStr)) {
				return new IndexParameter(NumberUtils.createInteger(indexStr), parameterName);
			}
		}
		return null;
	}

	/** index参数表示类 */
	private class IndexParameter {

		private Integer index;
		private String parameterName;

		public Integer getIndex() {
			return index;
		}

		public String getParameterName() {
			return parameterName;
		}

		public IndexParameter(Integer index, String parameterName) {
			super();
			this.index = index;
			this.parameterName = parameterName;
		}
	}

	/**
	 * 在创建的时候,设置实体的部分公共属性的默认值,比如:创建单位Id、创建日期、创建人Id
	 * 
	 * @param entity
	 *            指定实体
	 * @param dto
	 *            对应的DTO对象
	 */
	protected void setEntityCreateDefaultValue(POJO entity, DTO dto) {
		TbUser currentUser = UserUtils.getCurrentUser();
		if (null != currentUser) {
			entity.setCreateUserId(currentUser.getId());
		}
		TbUnit currentUnit = UserUtils.getCurrentUnit();
		String unitId = (null != dto ? dto.getOperatedUnitId() : null);
		unitId = (StringUtils.isNotBlank(unitId) ? unitId : (null != currentUnit ? currentUnit.getId() : null));
		entity.setCreateUnitId(unitId);
		entity.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
	}

	/**
	 * 在修改的时候,设置实体的部分公共属性的默认值,比如:修改单位Id、修改日期、修改人Id
	 * 
	 * @param entity
	 *            指定实体
	 * @param dto
	 *            对应的DTO对象
	 */
	protected void setEntityModifiedDefaultValue(POJO entity, DTO dto) {
		TbUser currentUser = UserUtils.getCurrentUser();
		entity = this.getDefaultDAO().get(dto.getId());
		if (null != currentUser) {
			entity.setModifiedUserId(currentUser.getId());
		}
		TbUnit currentUnit = UserUtils.getCurrentUnit();
		String unitId = (null != dto ? dto.getOperatedUnitId() : null);
		unitId = (StringUtils.isNotBlank(unitId) ? unitId : (null != currentUnit ? currentUnit.getId() : null));
		entity.setModifiedUnitId(unitId);
		entity.setModifiedDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
	}

	/**
	 * 在逻辑删除的时候,设置实体的逻辑删除标志和删除日期
	 * 
	 * @param entity
	 *            指定实体
	 */
	protected void setEntityLogicalDeleteValue(POJO entity) {
		TbUser currentUser = UserUtils.getCurrentUser();
		entity.setFlag(CommonConstants.LOGICAL_DELETE_FLAG);
		entity.setModifiedDate(dateFormat.format(new Date()));
		if (null != currentUser) {
			if (currentUser instanceof TbSuperUser) {
				entity.setModifiedUnitId(CommonConstants.ADMIN_UNIT_ID);
			} else {
				TbUnit unit = currentUser.getTbUnit();
				if (null != unit) {
					entity.setModifiedUnitId(unit.getId());
				} else {
					entity.setModifiedUnitId("UNKNOW");
				}
			}
			entity.setModifiedUserId(currentUser.getId());
		}
	}

	/**
	 * 在保存或者修改的时候,设置排除逻辑删除的标志位,避免恶意注入
	 * 
	 * @param dto
	 *            对应的DTO对象
	 */
	protected void setEntityLogicalDefaultValue(DTO dto) {
		List<String> excludeList = dto.getExcludeCopyValueFieldNames();
		if (null != excludeList) {
			excludeList.add("flag");
		}
	}

	/**
	 * 获取逻辑删除的条件对象
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回条件对象
	 * */
	protected Criterion getLogicalDeleteCriterion(DTO dto) {
		Criterion logicalDeleteCriterion = null;
		Criterion not = Restrictions.not(Restrictions.eq(((null == dto || StringUtils.isBlank(dto.getCriterionAlias())) ? "flag" : dto.getCriterionAlias() + ".flag"), CommonConstants.LOGICAL_DELETE_FLAG));
		Criterion isNull = Restrictions.isNull(((null == dto || StringUtils.isBlank(dto.getCriterionAlias())) ? "flag" : dto.getCriterionAlias() + ".flag"));
		logicalDeleteCriterion = Restrictions.or(not, isNull);
		return logicalDeleteCriterion;
	}

	/**
	 * 获取权限控制的条件对象
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回条件对象
	 * */
	protected Criterion getPermissionCriterion(DTO dto) {
		Criterion permissionCriterion = null;
		TbUser currentUser = UserUtils.getCurrentUser();
		if (null != currentUser) {
			if (!PermissionELFunction.isRoles(currentUser, StringUtils.split(CommonConstants.ADMIN_ROLE_CODE, ",")) && !(currentUser instanceof TbSuperUser)) {
				String[] unitIds = null;
				TbUnit unit = currentUser.getTbUnit();
				if (null != unit) {
					UurpAdaptor uurpAdaptor = SpringBeanManager.getUurpAdaptor();
					List<TbUnit> subUnits = uurpAdaptor.findUnitTree(unit.getId());
					subUnits.add(unit);
					unitIds = new String[subUnits.size()];
					for (int i = 0, len = subUnits.size(); i < len; i++) {
						TbUnit subUnit = subUnits.get(i);
						unitIds[i] = subUnit.getId();
					}
				}
				Criterion userCriterion = Restrictions.eq((StringUtils.isBlank(dto.getCriterionAlias()) ? "createUserId" : dto.getCriterionAlias() + ".createUserId"), currentUser.getId());
				if (ArrayUtils.isEmpty(unitIds)) {
					permissionCriterion = userCriterion;
				} else {
					Criterion unitCriterion = Restrictions.in((StringUtils.isBlank(dto.getCriterionAlias()) ? "createUnitId" : dto.getCriterionAlias() + ".createUnitId"), unitIds);
					permissionCriterion = Restrictions.or(userCriterion, unitCriterion);
				}
			}
		}
		return permissionCriterion;
	}

	/**
	 * 获取T类型的全部记录
	 * 
	 * @return T类型对象列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<POJO> listAll() {
		IResultSet<POJO> ressultSet = this.getDefaultDAO().listAll();
		return ressultSet.getResultList();
	}

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回对应实体
	 */
	@Override
	@Transactional(readOnly = true)
	public POJO findById(ID id) {
		POJO entity = this.getDefaultDAO().get(id);
		return entity;
	}

	/**
	 * 根据ID数组获取对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回对应实体对象列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<POJO> findByIds(ID[] ids) {
		List<POJO> entityList = new ArrayList<POJO>();
		for (int i = 0, len = ids.length; i < len; i++) {
			entityList.add(this.findById(ids[i]));
		}
		return entityList;
	}

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回对应实体
	 */
	@Override
	@Transactional(readOnly = true)
	public POJO loadById(ID id) {
		POJO entity = this.getDefaultDAO().load(id);
		return entity;
	}

	/**
	 * 根据ID数组获取对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回对应实体对象列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<POJO> loadByIds(ID[] ids) {
		List<POJO> entityList = new ArrayList<POJO>();
		for (int i = 0, len = ids.length; i < len; i++) {
			entityList.add(this.loadById(ids[i]));
		}
		return entityList;
	}

	/**
	 * 根据DTO查询对应的实体集合
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> findByDTO(DTO dto, Integer pageNumber, Integer pageSize) {
		return this.findByDTO(dto, pageNumber, pageSize, null);
	}

	/**
	 * 根据DTO查询对应的实体集合
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @param additionCriterionList
	 *            附加的查询条件对象列表
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> findByDTO(DTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList) {
		List<Criterion> criterionList = this.getPropertyCriterionByDTO(dto);
		if (CollectionUtils.isNotEmpty(additionCriterionList)) {
			criterionList.addAll(additionCriterionList);
		}
		IResultSet<POJO> pageList = this.getDefaultDAO().listByCriteria(pageNumber, pageSize, criterionList.toArray(new Criterion[0]), (ArrayUtils.isEmpty(dto.getCriterionOrders()) ? this.getDefaultDAO().getDefaultOrders() : dto.getCriterionOrders()), null);
		return pageList;
	}

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> findByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize) {
		return this.findByDTOOnPermission(dto, pageNumber, pageSize, null);
	}

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @param additionCriterionList
	 *            附加的查询条件对象列表
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> findByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion permissionCriterion = this.getPermissionCriterion(dto);
		if (CollectionUtils.isNotEmpty(additionCriterionList)) {
			criterionList.addAll(additionCriterionList);
		}
		if (null != permissionCriterion) {
			criterionList.add(permissionCriterion);
		}
		IResultSet<POJO> pageList = this.findByDTO(dto, pageNumber, pageSize, criterionList);
		return pageList;
	}

	/**
	 * 根据DTO查询对应的实体集合(该自动忽略被逻辑删除的记录，就是说flag为'D'的记录不会被查出来)
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> lFindByDTO(DTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		return this.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制(该自动忽略被逻辑删除的记录，就是说flag为'D'的记录不会被查出来)
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	@Override
	@Transactional(readOnly = true)
	public IResultSet<POJO> lFindByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		return this.findByDTOOnPermission(dto, pageNumber, pageSize, criterionList);
	}

	/**
	 * 根据ID删除对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回被删除的实体数
	 */
	@Override
	public Integer removeById(ID id) {
		return this.getDefaultDAO().deleteById(id);
	}

	/**
	 * 根据ID数组删除对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回被删除的实体数
	 */
	@Override
	public Integer removeByIds(ID[] ids) {
		Integer count = 0;
		for (int i = 0, len = ids.length; i < len; i++) {
			count += (this.removeById(ids[i]));
		}
		return count;
	}

	/**
	 * 根据ID逻辑删除对应的实体(逻辑删除只是将flag改成'D'标志)
	 * 
	 * @param id
	 *            ID
	 * @return 返回被删除的实体数
	 */
	@Override
	public Integer lRemoveById(ID id) {
		POJO entity = this.getDefaultDAO().get(id);
		setEntityLogicalDeleteValue(entity);
		this.update(entity);
		return 1;
	}

	/**
	 * 根据ID数组逻辑删除对应的实体(逻辑删除只是将flag改成'D'标志)
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回被删除的实体数
	 */
	@Override
	public Integer lRemoveByIds(ID[] ids) {
		Integer count = 0;
		for (int i = 0, len = ids.length; i < len; i++) {
			count += (this.lRemoveById(ids[i]));
		}
		return count;
	}

	/**
	 * 根据实体保存数据
	 * 
	 * @param entity
	 *            实体(游离态)
	 * @return 返回实体(持久态)
	 */
	@Override
	public POJO save(POJO entity) {
		this.getDefaultDAO().save(entity);
		return entity;
	}

	/**
	 * 根据DTO保存数据,期间会转成实体
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体,或者null
	 */
	@Override
	public POJO save(DTO dto) {
		POJO entity = null;
		try {
			entity = this.getDefaultDAO().getDefaultEntityClass().newInstance();
			dto.copyValuesTo(entity);
			setEntityCreateDefaultValue(entity, dto);
			entity = this.save(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据DTO保存数据,期间会转成实体(不保存逻辑删除字段flag,只能通过逻辑删除进行flag赋值)
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体,或者null
	 */
	@Override
	public POJO lSave(DTO dto) {
		setEntityLogicalDefaultValue(dto);
		return this.save(dto);
	}

	/**
	 * 根据实体更新数据
	 * 
	 * @param entity
	 *            实体(游离态)
	 * @return 返回实体(持久态)
	 */
	@Override
	public POJO update(POJO entity) {
		this.getDefaultDAO().update(entity);
		return entity;
	}

	/**
	 * 根据DTO更新数据,期间会转成实体
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体
	 */
	@Override
	public POJO update(DTO dto) {
		POJO entity = null;
		try {
			entity = this.getDefaultDAO().get(dto.getId());
			dto.copyValuesTo(entity);
			setEntityModifiedDefaultValue(entity, dto);
			entity = this.update(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据DTO更新数据,期间会转成实体(不保存逻辑删除字段flag,只能通过逻辑删除进行flag赋值)
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体
	 */
	@Override
	public POJO lUpdate(DTO dto) {
		setEntityLogicalDefaultValue(dto);
		return this.update(dto);
	}

	/**
	 * 获取默认DAO对象,即为操作T类型的DAO对象
	 * 
	 * @return 对应DAO对象
	 */
	protected abstract IBaseDAO<POJO, ID> getDefaultDAO();
}