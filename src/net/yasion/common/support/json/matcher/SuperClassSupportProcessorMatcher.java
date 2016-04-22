package net.yasion.common.support.json.matcher;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.processors.DefaultValueProcessorMatcher;

public class SuperClassSupportProcessorMatcher extends DefaultValueProcessorMatcher {

	private boolean superClassMatch = false;

	public SuperClassSupportProcessorMatcher() {
		super();
	}

	public SuperClassSupportProcessorMatcher(boolean superClassMatch) {
		super();
		this.superClassMatch = superClassMatch;
	}

	public boolean isSuperClassMatch() {
		return superClassMatch;
	}

	public void setSuperClassMatch(boolean superClassMatch) {
		this.superClassMatch = superClassMatch;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getMatch(Class target, Set set) {
		if (target != null && set != null) {
			if (superClassMatch) {
				if (set.contains(target)) {
					return target;
				} else {
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Object elem = it.next();
						boolean assignableFrom = ((Class) elem).isAssignableFrom(target);
						if (assignableFrom) {
							return elem;
						}
					}
				}
			} else {
				if (set.contains(target)) {
					return target;
				}
			}
		}
		return null;
	}
}