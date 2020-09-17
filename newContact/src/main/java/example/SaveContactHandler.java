package example;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;

public class SaveContactHandler extends ContactHandler<Contact, String> {

    public String handleRequest(
            Contact personRequest, Context context) {
        persistData(personRequest);
        return "Success";
    }

    private PutItemOutcome persistData(Contact contact)
            throws ConditionalCheckFailedException {
        return this.contactsTable
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withString("id", contact.getId())
                                .withString("firstName", contact.getFirstName())
                                .withString("lastName", contact.getLastName())
                                .withString("status", contact.getStatus())));
    }
}