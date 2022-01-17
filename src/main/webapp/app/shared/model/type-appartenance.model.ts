import { IAppartenance } from '@/shared/model/appartenance.model';

export interface ITypeAppartenance {
  id?: number;
  typeAppartenance?: string | null;
  libelle?: string | null;
  appartenance?: IAppartenance | null;
}

export class TypeAppartenance implements ITypeAppartenance {
  constructor(
    public id?: number,
    public typeAppartenance?: string | null,
    public libelle?: string | null,
    public appartenance?: IAppartenance | null
  ) {}
}
