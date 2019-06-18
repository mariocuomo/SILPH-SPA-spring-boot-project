package it.uniroma3.siw.demospring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Fotografia;
import it.uniroma3.siw.demospring.model.RigaOrdine;

@Component
public class Service {

	public List<Album> albumDiArtista(List<Album> albums, Long id) {
		ArrayList<Album> albumsDiArtista=new ArrayList<>();
		for (Album album : albums) {
			if(album.getFotografo().getId()==id)
				albumsDiArtista.add(album);
		}

		return albumsDiArtista;
	}

	public Long[] convertiStringInLong(String[] arrayDiFoto) {
		Long[] arraySupport = new Long [arrayDiFoto.length];
		for(int i=0; i<arrayDiFoto.length;i++) {
			arraySupport[i]=Long.valueOf(arrayDiFoto[i]).longValue();
		}
		return arraySupport;
	}

	public List<Fotografia> fotografieSelezionate(List<Fotografia> fotografie,  Long[] arrayDiFoto){
		ArrayList<Fotografia> fotografieSelezionatee=new ArrayList<>();
		for (Fotografia fotografia : fotografie) {
			for(int i=0; i<arrayDiFoto.length;i++) {
				if(fotografia.getId()==arrayDiFoto[i])
					fotografieSelezionatee.add(fotografia);
			}
		}
		return fotografieSelezionatee;
	
	}

	public List<RigaOrdine> creaRigheOrdine(List<Fotografia> selezionate) {
		ArrayList<RigaOrdine> righe = new ArrayList<RigaOrdine>();
		for (Fotografia fotografia : selezionate) {
			RigaOrdine ro = new RigaOrdine();
			ro.setFotografia(fotografia);
			righe.add(ro);
		}
		return righe;
	}

	public ArrayList<Fotografia> fotografieOrdinate(List<RigaOrdine> righeOrdine) {
		ArrayList<Fotografia> fotografieOrdinatee = new ArrayList<Fotografia>();
		for (RigaOrdine rigaOrdine : righeOrdine) {
			Fotografia fotografia = new Fotografia();
			fotografia = rigaOrdine.getFotografia();
			fotografieOrdinatee.add(fotografia);
		}
		return fotografieOrdinatee;
	}

}
