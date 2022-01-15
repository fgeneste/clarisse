import { IPersonne } from '@/shared/model/personne.model';

export interface INoms {
  id?: number;
  qualite?: string | null;
  nomUsuel?: string | null;
  prenomUsuel?: string | null;
  nomPatronymique?: string | null;
  prenomCivil?: string | null;
  nomMarital?: string | null;
  nomDistinctif?: string | null;
  nomMajuscule?: string | null;
  nomTechnique?: string | null;
  feminisation?: string | null;
  dateDebut?: Date | null;
  dateFin?: Date | null;
  personne?: IPersonne | null;
}

export class Noms implements INoms {
  constructor(
    public id?: number,
    public qualite?: string | null,
    public nomUsuel?: string | null,
    public prenomUsuel?: string | null,
    public nomPatronymique?: string | null,
    public prenomCivil?: string | null,
    public nomMarital?: string | null,
    public nomDistinctif?: string | null,
    public nomMajuscule?: string | null,
    public nomTechnique?: string | null,
    public feminisation?: string | null,
    public dateDebut?: Date | null,
    public dateFin?: Date | null,
    public personne?: IPersonne | null
  ) {}
}
