package main.domain.arquivo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class EscritaArq {

    Collection lista;
    String nomeArq;

    public EscritaArq(Collection lista, String nomeArq) {
        this.lista = lista;
        this.nomeArq = nomeArq;
    }

    // serialização: gravando o objetos no arquivo binário "nomeArq"
    public void gravarArquivoBinario() {
        File arq = new File(nomeArq);
        try {
            arq.delete();
            arq.createNewFile();

            ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arq));
            objOutput.writeObject(lista);
            objOutput.close();

        } catch (IOException erro) {
            System.out.printf("Erro: %s", erro.getMessage());
        }
    }

}
