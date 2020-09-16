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

public class SaveContactHandler
        implements RequestHandler<Contact, String> {

    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "contacts-lucas";
    private Regions REGION = Regions.US_EAST_1;

    public String handleRequest(
            Contact personRequest, Context context) {
        context.getLogger().log("Log 1");
        this.initDynamoDbClient(context);
        context.getLogger().log("Log 2");

        persistData(personRequest);
        context.getLogger().log("Log 3");

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

    private void initDynamoDbClient(Context context) {
        context.getLogger().log("Log 1.1");
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        context.getLogger().log("Log 1.2");
        client.setRegion(Region.getRegion(REGION));
        context.getLogger().log("Log 1.3");
        this.dynamoDb = new DynamoDB(client);
        context.getLogger().log("Log 1.4");
    }
}