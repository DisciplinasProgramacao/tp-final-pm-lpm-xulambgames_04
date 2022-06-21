package main.domain.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import main.domain.cliente.Cliente;

public class LeituraArq {

    String nomeArq;

    public LeituraArq(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    // desserialização: recuperando os objetos gravados no arquivo binário "nomeArq"
    public ArrayList<Cliente> lerArquivoBinario() {
        ArrayList<Cliente> lista = new ArrayList();
        try {
            File arq = new File(nomeArq);
            if (arq.exists()) {
                ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
                lista = (ArrayList<Cliente>) objInput.readObject();
                objInput.close();
            }
        } catch (IOException erro1) {
            System.out.printf("Erro: %s", erro1.getMessage());
        } catch (ClassNotFoundException erro2) {
            System.out.printf("Erro: %s", erro2.getMessage());
        }

        return (lista);
    }
}
