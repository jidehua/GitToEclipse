package MingL.Test;

import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class QuTest {

	@Test
	public void testQueryIndex() throws Exception {

		// 指定索引库存放的路径
		String syPath = "D:\\Lunece\\我的索引库";
		Directory directory = FSDirectory.open(Paths.get(syPath));
		// 创建indexReader对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建indexSearcher对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 创建查询
		Query query = new TermQuery(new Term("fileName", "apache"));
		// 执行查询
		// 第一个参数是查询对象，第二个参数是结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);
		// 查询结果的总条数
		System.out.println("总条数=" + topDocs.totalHits);
		// 遍历查询结果
		// topDocs中存储了document对象的id
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// ScoreDoc为document的id
			// 根据id查找document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("fileName"));
			System.out.println(document.get("fileContent"));
			System.out.println(document.get("filePath"));
			System.out.println(document.get("fileSize"));
		}
		indexReader.close();
	}
}
