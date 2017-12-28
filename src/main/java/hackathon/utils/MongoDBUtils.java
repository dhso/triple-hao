package hackathon.utils;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBUtils {

	public static String DB_IP = "10.207.2.144";

	public static int DB_PORT = 40001;

	public static String DB_DATABASE_NAME = "triple_hao";

	public static String DB_COLLECTION_PROCESS = "process";
	public static String DB_COLLECTION_CUSTOMER = "customer";

	public static String insertProcess(String fileName) {
		Document document = new Document("status", "processing").append("fileName", fileName).append("start_time",
				(new Date()).getTime());
		try {
			MongoClient mongoClient = new MongoClient(DB_IP, DB_PORT);
			MongoDatabase mgdb = mongoClient.getDatabase(DB_DATABASE_NAME);
			MongoCollection<Document> collection = mgdb.getCollection(DB_COLLECTION_PROCESS);

			collection.insertOne(document);

			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return document.get("_id").toString();
	}

	public static String getProcessStatusById(String id) {
		try {
			MongoClient mongoClient = new MongoClient(DB_IP, DB_PORT);
			MongoDatabase mgdb = mongoClient.getDatabase(DB_DATABASE_NAME);
			MongoCollection<Document> collection = mgdb.getCollection(DB_COLLECTION_PROCESS);

			Document doc = collection.find(new BasicDBObject("_id", new ObjectId(id))).first();

			mongoClient.close();

			if (doc != null) {
				return doc.getString("status");
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return "Don't find the process";
	}

	public static long getCountByCondition(BasicDBObject query) {
		long result = 0;
		try {
			MongoClient mongoClient = new MongoClient(DB_IP, DB_PORT);
			MongoDatabase mgdb = mongoClient.getDatabase(DB_DATABASE_NAME);
			MongoCollection<Document> collection = mgdb.getCollection(DB_COLLECTION_CUSTOMER);
			result = collection.count(query);

			mongoClient.close();
			return result;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return 0;
	}

	public static boolean updateProcess(String id) {
		Document doc = new Document("status", "done").append("end_time", (new Date()).getTime());
		try {
			MongoClient mongoClient = new MongoClient(DB_IP, DB_PORT);
			MongoDatabase mgdb = mongoClient.getDatabase(DB_DATABASE_NAME);
			MongoCollection<Document> collection = mgdb.getCollection(DB_COLLECTION_PROCESS);

			collection.updateMany(Filters.eq("_id", new ObjectId(id)), new Document("$set", doc));

			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}

}
