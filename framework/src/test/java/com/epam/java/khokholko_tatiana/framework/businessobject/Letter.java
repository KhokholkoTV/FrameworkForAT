package com.epam.java.khokholko_tatiana.framework.businessobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Class binary
 * 
 * @author Tatiana Khokholko
 */
public class Letter {

	private String recipient;
	private String subj;
	private String text;

	public Letter(String recipient, String subj, String text) {
		this.recipient = recipient;
		this.subj = subj;
		this.text = text;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubj() {
		return subj;
	}

	public void setSubj(String subj) {
		this.subj = subj;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
