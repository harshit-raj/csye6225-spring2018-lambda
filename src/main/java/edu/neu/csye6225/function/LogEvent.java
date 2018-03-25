package edu.neu.csye6225.function;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogEvent implements RequestHandler<SNSEvent, Object> {

  DynamoDB dynamoDB;

  public Object handleRequest(SNSEvent request, Context context) {

    this.initDyna();


    String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

    context.getLogger().log("Invocation started: " + timeStamp);

    context.getLogger().log("1: " + (request == null));

    context.getLogger().log("2: " + (request.getRecords().size()));

    String payload = request.getRecords().get(0).getSNS().getMessage();

    context.getLogger().log(payload);

    timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

    context.getLogger().log("Invocation completed: " + timeStamp);

    PutItemOutcome outC = this.dynamoDB.getTable("csye6225").putItem(new PutItemSpec().withItem(new Item().withString("id",payload)));

    context.getLogger().log("Done!!!");






    return null;
  }

  private void initDyna(){
    AmazonDynamoDBClient client = new AmazonDynamoDBClient();
    client.setRegion(Region.getRegion(Regions.US_EAST_1));
    this.dynamoDB = new DynamoDB(client);
  }

}

