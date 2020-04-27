/**
 * 
 */
package com.pratik.shopping.inventory;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pratik Das
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("InventoryItem")
public class InventoryItem {
	
	@Id
	private String itemID;
	private String productName;
	private Integer itemsInStock;

}
