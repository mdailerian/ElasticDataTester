
/**
 * Created by MMAA-local on 12/23/2015.
 */
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParser;
//import jdk.nashorn.internal.ir.ObjectNode;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

//import org.elasticsearch.index.mapper.object.ObjectMapper;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.node.NodeBuilder;
//import java.io.File;
//            http://teknosrc.com/elasticsearch-java-api-get-index-list/
//            http://teknosrc.com/execute-raw-elasticsearch-query-using-transport-client-java-api/
//            http://www.javacodegeeks.com/2013/04/getting-started-with-elasticsearch.html
//            http://docs.spring.io/spring-data/elasticsearch/docs/1.0.0.M1/reference/html/elasticsearch.repositories.html
//      http://stackoverflow.com/questions/33909389/how-to-write-code-for-search-in-elasticsearch-using-jest-client-in-java

//SEE RESULTS: examine sample data with Sense - http://localhost:5601/app/sense
// JAVADOCS: http://javadoc.kyubu.de/elasticsearch/HEAD/allclasses-noframe.html

public class TestDriver {

    public IndexResponse indexDocument( Client client, XContentBuilder doc) throws IOException{
        // create an index, stick some data
            IndexResponse response = client.prepareIndex("dailema2", "test" )
                    .setSource( doc )
                    .execute()
                    .actionGet();

        return response;
    }

    public static void searchDocument(Client client, String index, String type,
                                      String field, String value){

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(termQuery(field, value))//fieldQuery(field, value))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] results = response.getHits().getHits();

        System.out.println("Current results: " + results.length);
        for (SearchHit hit : results) {
            System.out.println("------------------------------");
            Map<String,Object> result = hit.getSource();
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        System.out.println( "Driver Started...");

        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

            // build a dummy document to index in ES
            XContentBuilder doc = jsonBuilder()
                        .startObject()
                        .field("job", "driver")
                        .field("message", "Be smart")
                        .endObject();

            TestDriver td = new TestDriver();
        //    IndexResponse response = td.indexDocument(client, doc );
        //    System.out.println(" Response : " + response );

            td.searchDocument( client, "dailema2", "test", "job", "driver");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on shutdown
        client.close();
    }
}

//JsonParser parser = new JsonFactory()
//        .createParser(new File("C:\\Users\\MMAA-local\\Desktop\\Marti dev\\moviedata.json"));
//            System.out.println( "JSON File " + parser );
