import { INuancePolitique } from '@/shared/model/nuance-politique.model';
import { INoms } from '@/shared/model/noms.model';
import { IAppartenance } from '@/shared/model/appartenance.model';

export interface IPersonne {
  id?: number;
  matricule?: string | null;
  dateDeNaissance?: Date | null;
  lieuDeNaissance?: string | null;
  departementDeNaissance?: number | null;
  dateDeDeces?: Date | null;
  lieuDeDeces?: string | null;
  departementDeDeces?: number | null;
  profession?: string | null;
  diplome?: string | null;
  decoration?: string | null;
  rangProtocolaire?: number | null;
  cspInsee?: string | null;
  nuancePolitique?: INuancePolitique | null;
  noms?: INoms[] | null;
  appartenances?: IAppartenance[] | null;
}

export class Personne implements IPersonne {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public dateDeNaissance?: Date | null,
    public lieuDeNaissance?: string | null,
    public departementDeNaissance?: number | null,
    public dateDeDeces?: Date | null,
    public lieuDeDeces?: string | null,
    public departementDeDeces?: number | null,
    public profession?: string | null,
    public diplome?: string | null,
    public decoration?: string | null,
    public rangProtocolaire?: number | null,
    public cspInsee?: string | null,
    public nuancePolitique?: INuancePolitique | null,
    public noms?: INoms[] | null,
    public appartenances?: IAppartenance[] | null
  ) {}
}
