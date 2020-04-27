/**
 * 
 */
package com.pratik.shopping.inventory.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pratik.shopping.inventory.InventoryItem;

/**
 * @author Pratik Das
 *
 */
@Repository
public interface InventoryRepository extends MongoRepository<InventoryItem, String>{
	
	//@Query(value = "{'employees.name': ?0}", fields = "{'employees' : 0}")
	InventoryItem findInventoryItemByProductName(String name);

    List<InventoryItem> findInventoryItemsByProductName(String name);

}
