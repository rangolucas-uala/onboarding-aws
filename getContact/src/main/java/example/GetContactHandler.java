package example;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class GetContactHandler implements RequestHandler<Map<String, String>, String> {

    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "contacts-lucas";
    private Regions REGION = Regions.US_EAST_1;

    public GetContactHandler() {
        this.initDynamoDbClient();
    }

    public String handleRequest(Map<String, String> event, Context context) {
        event.entrySet().forEach(e -> context.getLogger().log(e.getKey() + " - " + e.getValue()));
        return "Success";
    }

    private PutItemOutcome persistData(Contact contact)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withString("id", contact.getId())
                                .withString("firstName", contact.getFirstName())
                                .withString("lastName", contact.getLastName())
                                .withString("status", contact.getStatus())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }
}