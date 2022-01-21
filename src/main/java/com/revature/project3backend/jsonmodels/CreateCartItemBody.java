package com.revature.project3backend.jsonmodels;

import lombok.Data;

/**
 * CreateCartItemBody is used as the body for the method in CartItemController that creates a CartItem
 */
@Data
public class CreateCartItemBody {
	private Integer productId;
	private Integer quantity;
}
