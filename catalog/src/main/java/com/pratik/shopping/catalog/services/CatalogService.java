/**
 * 
 */
package com.pratik.shopping.catalog.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pratik.shopping.catalog.CatalogItem;
import com.pratik.shopping.catalog.InventoryItem;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

/**
 * @author fab
 *
 */
@Service
public class CatalogService {

	public List<CatalogItem> getCatalogItemsByCategory(final String category) {

		List<CatalogItem> catalogItems = new ArrayList<>();
		// fetch price from pricing engine microservice
		List<InventoryItem> inventoryItems = fetchInventoryItems(category) ;

		// fetch items in stock from inventory microservice
		inventoryItems.forEach(item->{
			double price = fetchPrice(item.getName());
			CatalogItem catalogItem = CatalogItem.builder().id(UUID.randomUUID().toString())
					.category("cat 1")
					.itemsInStock(item.getItemsInStock())
					.price(price)
					.name(item.getName())
					.build();
			catalogItems.add(catalogItem );
		});

		return catalogItems;
	}

	private double fetchPrice(final String itemName) {
		Map<String, Double> priceMap = new HashMap<>();

		return 1.5;

	}

	private List<InventoryItem> fetchInventoryItems(final String category) {
		List<InventoryItem> inventoryItems = new ArrayList<>();
		
		InventoryItem e;
		for (int i = 0; i < 15; i++) {
			e = InventoryItem.builder().name("name "+i).itemsInStock(i + 1).build();
			inventoryItems.add(e);
		}
		
		return inventoryItems;
	}

	private WebClient getWebClient() {
		TcpClient tcpClient = TcpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.doOnConnected(connection -> {
					connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
					connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
				});

		WebClient client = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient))).build();
		return client;
	}

}
