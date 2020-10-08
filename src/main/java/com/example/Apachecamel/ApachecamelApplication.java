package com.example.Apachecamel;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApachecamelApplication extends RouteBuilder{

	public static void main(String[] args) {
		SpringApplication.run(ApachecamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		// let's move data from a file to another 
		System.out.println("let go");
		//moveAllFiles()
		//moveSpecificFile("file1");
		//moveWithBodyContent("world");
		//processFileToCsv();
		multiprocess();
		System.out.println("end");
		
	}
	
	public void moveAllFiles() {
		//noop is to not create the camel folder
		from("file:source?noop=true").to("file:destinationf");
	}
	public void moveSpecificFile(String type) {
		from("file:source?noop=true").filter(header(Exchange.FILE_NAME).startsWith("file1")).to("file:destinationf");
	}
	
	public void moveWithBodyContent(String content) {
		from("file:source?noop=true").filter(body().contains(content)).to("file:destinationf");
	}
	
	public void processFileToCsv() {
		from("file:source?noop=true").process(p->{
			String body = p.getIn().getBody(String.class);
			StringBuilder sb = new StringBuilder();
			Arrays.stream(body.split(" ")).forEach(s->{
				sb.append(s+",");
			});
			p.getIn().setBody(sb);
		}).to("file:destinationf?fileName=names.csv");
	}
	public void multiprocess() {
		from("file:source?noop=true").unmarshal().csv().split(body().tokenize(",")).choice()
		.when(body().contains("Closed")).to("file:destinationf?fileName=close.csv")
		.when(body().contains("Pending")).to("file:destinationf?fileName=pending.csv")
		.when(body().contains("Interest")).to("file:destinationf?fileName=interest.csv");
	}
}
