import { IStructure } from '@/shared/model/structure.model';

export interface ITypeStructure {
  id?: number;
  codeTypeStructure?: string | null;
  libelle?: string | null;
  libelleCourt?: string | null;
  libellePluriel?: string | null;
  urlComplete?: string | null;
  urlSimplifie?: string | null;
  ordre?: number | null;
  structure?: IStructure | null;
}

export class TypeStructure implements ITypeStructure {
  constructor(
    public id?: number,
    public codeTypeStructure?: string | null,
    public libelle?: string | null,
    public libelleCourt?: string | null,
    public libellePluriel?: string | null,
    public urlComplete?: string | null,
    public urlSimplifie?: string | null,
    public ordre?: number | null,
    public structure?: IStructure | null
  ) {}
}
