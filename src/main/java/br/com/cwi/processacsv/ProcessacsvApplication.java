package br.com.cwi.processacsv;

import br.com.cwi.processacsv.service.ProcessadorCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ProcessacsvApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(ProcessacsvApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProcessacsvApplication.class, args).close();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		if (args.getNonOptionArgs().size()>0) {
			for (String arquivo : args.getNonOptionArgs()) {
				logger.info("Processando arquivo {}", arquivo);
				ProcessadorCsv processadorCsv = new ProcessadorCsv();
				processadorCsv.execute(arquivo);
				logger.info("Processamento do arquivo {} finalizado", arquivo);
			}
		} else {
			logger.info("Nenhum argumento informado");
		}
	}
}
