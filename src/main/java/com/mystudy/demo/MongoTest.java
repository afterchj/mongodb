package com.mystudy.demo;

import java.util.List;


import com.mystudy.util.MongoDb;
import org.bson.Document;


import com.mongodb.client.result.UpdateResult;
import org.junit.Before;
import org.junit.Test;


public class MongoTest
{
    @Before
    public void before(){
        MongoDb.connect("test", "mytest", "10.10.11.130", 27017);
    }

    @Test
    public void testInsert(){
        Document document = new Document();
        document.append("_id", "10120").append("name", "after").append("gender", "male");
        MongoDb.insert(document);
        System.out.println("gender="+document.get("gender"));
    }

    @Test
    public void testFindAll(){
        List<Document> results = MongoDb.findAll();
        for(Document doc : results){
            System.out.println(doc.toJson());
        }
    }

    @Test
    public void testFindBy(){
        Document filter = new Document();
        filter.append("name", "lucy");
        List<Document> results = MongoDb.findBy(filter);
        for(Document doc : results){
            System.out.println(doc.toJson());
        }
    }

    @Test
    public void testUpdateOne(){
        Document filter = new Document();
        filter.append("_id", "10120");

        //注意update文档里要包含"$set"字段
        Document update = new Document();
        update.append("$set", new Document("gender", "female").append("name", "lucy"));
        UpdateResult result = MongoDb.updateOne(filter, update);
        System.out.println("matched count = " + result.getMatchedCount());
    }

    @Test
    public void testUpdateMany(){
        Document filter = new Document();
        filter.append("gender", "female");

        //注意update文档里要包含"$set"字段
        Document update = new Document();
        update.append("$set", new Document("gender", "male"));
        UpdateResult result = MongoDb.updateMany(filter, update);
        System.out.println("matched count = " + result.getMatchedCount());
    }

    @Test
    public void testReplace(){
        Document filter = new Document();
        filter.append("name", "wu");

        //注意：更新文档时，不需要使用"$set"
        Document replacement = new Document();
        replacement.append("value", 123);
        MongoDb.replace(filter, replacement);
    }

    @Test
    public void testDeleteOne(){
        Document filter = new Document();
        filter.append("value", 123);
        MongoDb.deleteOne(filter);
    }

    @Test
    public void testDeleteMany(){
        Document filter = new Document();
        filter.append("gender", "male");
        MongoDb.deleteMany(filter);
    }
}
