import { ITypeAppartenance } from '@/shared/model/type-appartenance.model';
import { IStructure } from '@/shared/model/structure.model';
import { IPersonne } from '@/shared/model/personne.model';

export interface IAppartenance {
  id?: number;
  matricule?: string | null;
  dateDebut?: Date | null;
  dateFin?: Date | null;
  dateElection?: Date | null;
  observation?: string | null;
  departement?: number | null;
  libelle?: string | null;
  article?: string | null;
  motifDeDebut?: string | null;
  commentaireDeChangement?: string | null;
  typeAppartenance?: ITypeAppartenance | null;
  structure?: IStructure | null;
  personne?: IPersonne | null;
}

export class Appartenance implements IAppartenance {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public dateDebut?: Date | null,
    public dateFin?: Date | null,
    public dateElection?: Date | null,
    public observation?: string | null,
    public departement?: number | null,
    public libelle?: string | null,
    public article?: string | null,
    public motifDeDebut?: string | null,
    public commentaireDeChangement?: string | null,
    public typeAppartenance?: ITypeAppartenance | null,
    public structure?: IStructure | null,
    public personne?: IPersonne | null
  ) {}
}
