package example;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public abstract class ContactHandler<RQ, RP> implements RequestHandler<RQ, RP> {

    Table contactsTable;
    private static final String DYNAMODB_TABLE_NAME = "contacts-lucas";
    private static final Regions REGION = Regions.US_EAST_1;

    public ContactHandler() {
        this.initDynamoDbClient();
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        builder.setRegion(REGION.getName());
        AmazonDynamoDB client = builder.build();
        this.contactsTable = new DynamoDB(client).getTable(DYNAMODB_TABLE_NAME);
    }
}
