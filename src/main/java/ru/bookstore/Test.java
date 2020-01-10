package ru.bookstore;

/**
 * Created by Rubanov.Maksim on 09.01.2020.
 */

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bookstore.domain.Book;
import ru.bookstore.domain.PublishingHouse;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Test {

    private int shardCount = 5;
    private Shard[] shards = new Shard[]{
            new Shard("dbUrl_1"),
            new Shard("dbUrl_2"),
            new Shard("dbUrl_3"),
            new Shard("dbUrl_4"),
            new Shard("dbUrl_5"),
    };

    private static String formSqlShardCriteria(String name) {
        String result = "like '%";
        String or = "";
        String[] worlds = name.trim().split("\\s+");
        // если всего одно слово в названии - просто возвращаем его
        if(worlds.length == 1){
            return name;
        }
        for (int i = 0; i < worlds.length && i < 2; i++) {
            ///не учитываем в поиске предлоги, пропускаем такие слова, длинна колорых меньше 3-х букв1
            if (worlds[i].length() < 3) {
                continue;
            }
            //4 буквы или 3, если слово из 3-x букв.
            result = result + new String(Arrays.copyOf(worlds[i].toCharArray(), 4)).trim() + '%';
            or = new String(Arrays.copyOf(worlds[i].toCharArray(), 4)).trim() + '%' + or;
        }
        or = " or like '%" + or + "'";
        return result + "' " + or;
    }

    private static int sha256(String data) {
        MessageDigest md;
        StringBuilder sb = new StringBuilder();
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            // bytes to hex

            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            BigInteger num = new BigInteger(sb.toString(), 16);
            sb.setLength(0);
            return num.intValue();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println(1%5);
        System.out.println(2%5);
        System.out.println(3%5);
        System.out.println(4%5);
        System.out.println(5%5);
        System.out.println(6%5);
        System.out.println(7%5);
        System.out.println(8%5);
        System.out.println(9%5);
        System.out.println(10%5);
        System.out.println(11%5);
        System.out.println(12%5);
        System.out.println(13%5);
        System.out.println(14%5);
        System.out.println(15%5);
        System.out.println(16%5);
        System.out.println(1412313123%5);

        System.out.println(25%5);






        if(true) return;


        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_00_000; i++) {
            String str = " java полное руководство пп ";
//            String str = "Север";


//			List<String> test = Stream.of(str.trim().split("\\s+")).filter(predicate->predicate.length()>3).collect(Collectors.toList());
//			List<String> test = Arrays.asList(str.trim().split("\\s+")).stream().filter(predicate->predicate.length()>3).collect(Collectors.toList());
            String hh = formSqlShardCriteria(str);
            long dd = sha256(hh);

            System.out.println(i + " " + hh + " -- " + dd);

//		//	if(true )break;
//
//			String ss = "";
//			for(int j = 0; j < test.size() && j  < 3 ; j ++) {
//				ss = ss + " like '";
//				for (int j2 = 0; j2 < test.get(j).length(); j2++) {
//					ss = ss + test.get(j).charAt(j2);
//				}
//				ss = ss +"%'";
//			}
            //	System.out.println(ss);
        }


        System.out.println(System.currentTimeMillis() - start);

        if (true) return;


        String hash = "179df9e314cbd8273ffb7dd11b607588803040842a6580cd4b39f7b826b70e06";

        BigInteger kk = new BigInteger(hash, 16);
        System.out.println(kk.longValue());


        System.out.println(Long.toString(5420635996812676614l, 16));


    }


    ///////////////////////////////////


    @ApiOperation(value = "Метод для добавления книги в БД", response = Book.class, tags = "addBook")
    @PostMapping("/books/new")
    public ResponseEntity addBook(@RequestBody(required = true) Book book) {


        String sql = formSqlShardCriteria(book.getName());
        int hash = sha256(sql);
        int shardNum = hash % shardCount;
        shards[shardNum].insert(book);

        return ResponseEntity.ok().body(book);
    }


    @ApiOperation(value = "Метод поиска книг", response = Book.class, tags = "addBook")
    @GetMapping("/books/find/{requestString}")
    public ResponseEntity<List<Book>> findBooks(@PathVariable("requestString") String requestString) {
        List<Book> result = new ArrayList<>();

        if( requestString == null || requestString.isEmpty() ){
            ResponseEntity.ok().body(result);
        }
        String[] worlds = requestString.trim().split("\\s+");
        //если в поисковом запросе только одно слово, тоискать надо везде
        if(worlds.length == 1){
            result =  fullSearch(requestString, null);
            return ResponseEntity.ok().body(result);
        }

        String sql = formSqlShardCriteria(requestString);
        int hash = sha256(sql);
        int shardNum = hash % shardCount;

        result = shards[shardNum].find(requestString);

        // проверяем , нашли ли книги на нужном shard, если ихз нет - нудно искать на оставшихся,
        // т.к.  критерии могу быть не полными.
        if (result.size() == 0 ){
            result =  fullSearch(requestString, shards[shardNum]);
        }

        return ResponseEntity.ok().body(result);
    }

    private List<Book> fullSearch(String searchString,Shard exceptShard){
        List<Book> result = new ArrayList<>();
        // ......................
        //TODO  здесь формируем строку поиска ну или объект поиска, смотря как будем реализовывать.
        //......................

        for(Shard shard: shards){
            // исключаем из поиска shard, если передали не пустое значение его.
            // Это нужно например если мы не нащшли по запросу в одном из них и поняли что надо искать по оставшимся.
            // либо какой то отвалился и мы об этом узнали.
            if(shard == exceptShard){
                continue;
            }
            // резыльтаты поиска по каждому shard заносим в общий результат.
            result.addAll( shard.find(searchString) );
        }
        return result;
    }

}


class Shard {
    private String url;

    public Shard(String url) {
        this.url = url;
    }

    public void insert(Book book) {
        //................  заводим новую книгу
    }

    public List<Book> find(String searchString) {
        List<Book> result = null;
        //............
        return result;
    }
}