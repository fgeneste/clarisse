import { IPersonne } from '@/shared/model/personne.model';

export interface INuancePolitique {
  id?: number;
  codeNuancePolitique?: string | null;
  libelle?: string | null;
  personnes?: IPersonne[] | null;
}

export class NuancePolitique implements INuancePolitique {
  constructor(
    public id?: number,
    public codeNuancePolitique?: string | null,
    public libelle?: string | null,
    public personnes?: IPersonne[] | null
  ) {}
}
