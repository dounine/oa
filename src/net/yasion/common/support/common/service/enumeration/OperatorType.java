package net.yasion.common.support.common.service.enumeration;

/** 操作类型定义枚举类 */
public enum OperatorType {
	/** 等于 */
	EQ,
	/** 不等于 */
	NOTEQ,
	/** 类似于 */
	LIKE,
	/** 全等类似于 */
	FULLLIKE,
	/** 前面类似于 */
	STARTLIKE,
	/** 末尾类似于 */
	ENDLIKE,
	/** 不类似于 */
	NOTLIKE,
	/** 不全等类似于 */
	NOTFULLLIKE,
	/** 不前面类似于 */
	NOTSTARTLIKE,
	/** 不末尾类似于 */
	NOTENDLIKE,
	/** 类似于(忽略大小写) */
	ILIKE,
	/** 全等类似于(忽略大小写) */
	IFULLLIKE,
	/** 前面类似于(忽略大小写) */
	ISTARTLIKE,
	/** 末尾类似于(忽略大小写) */
	IENDLIKE,
	/** 不类似于(忽略大小写) */
	INOTLIKE,
	/** 不全等类似于(忽略大小写) */
	INOTFULLLIKE,
	/** 不前面类似于(忽略大小写) */
	INOTSTARTLIKE,
	/** 不末尾类似于(忽略大小写) */
	INOTENDLIKE,
	/** 大于 */
	GT,
	/** 小于 */
	LT,
	/** 在于 */
	IN,
	/** 不在于 */
	NOTIN,
	/** 不等于 */
	NE,
	/** 大于等于 */
	GE,
	/** 小于等于 */
	LE,
	/** 为空 */
	ISNULL,
	/** 不为空 */
	ISNOTNULL,
	/** 无值 */
	ISEMPTY,
	/** 非无值 */
	ISNOTEMPTY,
	/** 在...之间 */
	BETWEEN,
	/** 不在...之间 */
	NOTBETWEEN
}