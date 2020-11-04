package br.com.cwi.processacsv.service;

import br.com.cwi.processacsv.ProcessacsvApplication;
import br.com.cwi.processacsv.model.Conta;
import br.com.cwi.processacsv.model.ContaAtualizada;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessadorCsv {

	private static final Logger logger = LoggerFactory.getLogger(ProcessadorCsv.class);

	private <T> List<T> readCsv(Class<T> clazz, String path) throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(path));

		CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader)
				.withSeparator(';')
				.withType(clazz)
				.build();

		List<T> contas = csvToBean.parse();

		for (T conta : contas)
			System.out.println(conta);
		return contas;
	}

	private void writeCsv(List<ContaAtualizada> contas, String path) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Writer writer = Files.newBufferedWriter(Paths.get(path));
		StatefulBeanToCsv<ContaAtualizada> beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').build();

		beanToCsv.write(contas);

		writer.flush();
		writer.close();
	}

	public void execute(String csvFilePath){
		List<Conta> contas;
		List<ContaAtualizada> contasAtualizadas;
		try {
			contas = readCsv(Conta.class, csvFilePath);
			String newName = csvFilePath.replace(".", ".resultado.");
			ReceitaService receitaService = new ReceitaService();
			contasAtualizadas = contas.stream().map(conta ->{
				ContaAtualizada contaAtualizada = new ContaAtualizada();
				boolean retorno;
				try {
					contaAtualizada.init(conta);
					retorno = receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), conta.getSaldo(), conta.getStatus());
					contaAtualizada.setRetornoReceita(retorno);
				} catch (InterruptedException e) {
					e.printStackTrace();
					contaAtualizada.setRetornoReceita(false);
				}
				return contaAtualizada;
			}).collect(Collectors.toList());
			writeCsv(contasAtualizadas, newName);
			logger.info("Processamento finalizado. Exibindo resultados:");
			readCsv(ContaAtualizada.class, newName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		}

	}
}
