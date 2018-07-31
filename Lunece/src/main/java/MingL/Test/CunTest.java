package MingL.Test;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.lucene.HanLPAnalyzer;

public class CunTest {

	@Test
	public void createIndex() throws Exception {
		// 指定索引库路径
		String syPath = "D:\\Lunece\\我的索引库";
		// 打开目录
		Directory directory = FSDirectory.open(Paths.get(syPath));
		// 创建分词器对象
		//StandardAnalyzer analyzer = new StandardAnalyzer();
         Analyzer  analyzer=new HanLPAnalyzer();
		// 创建索引配置信息对象
		IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);

		// 创建索引写对象
		IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

		// 执行数据源的位置
		File dir = new File("D:\\框架课程\\lucene\\资料\\searchsource");
		for (File f : dir.listFiles()) {
			// 文件名
			String fileName = f.getName();
			// 文件内容
			String fileContent = FileUtils.readFileToString(f);
			// 文件路径
			String filePath = f.getAbsolutePath();
			// 文件大小
			long fileSize = FileUtils.sizeOf(f);
/*			System.out.println("文件名："+fileName);
			System.out.println("文件内容："+fileContent);
			System.out.println("文件路径："+filePath);
			System.out.println("文件大小："+fileSize);*/
			
			// 创建文件名域
			// 第一参数：域名称；第二参数：域内容；第三参数：是否储存

			Field fieldName = new TextField("fileName", fileName, Field.Store.YES);

			// 文件内容域
			Field fieldContent = new TextField("fieldContent", fileContent, Field.Store.YES);

			// 文件路径域
			Field fieldPath = new StoredField("path", filePath);

			// 文件的大小
			Field fieldSize = new StoredField("size", fileSize);
			// 创建document对象
			Document document = new Document();
			document.add(fieldName);
			document.add(fieldContent);
			document.add(fieldPath);
			document.add(fieldSize);
			// 创建索引，病写入索引库
			indexWriter.addDocument(document);
		}
		indexWriter.commit();
		indexWriter.close();

	}

}
