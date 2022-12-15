package com.fronchak.petshop.domain.dtos.color;

import java.io.Serializable;

import com.fronchak.petshop.domain.validations.ColorInsertValid;

@ColorInsertValid
public class InsertColorDTO extends InputColorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

}
