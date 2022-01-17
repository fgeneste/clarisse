import { ITypeStructure } from '@/shared/model/type-structure.model';
import { IAppartenance } from '@/shared/model/appartenance.model';

export interface IStructure {
  id?: number;
  codeStructure?: string | null;
  dateDeCreation?: Date | null;
  dateDeCloture?: Date | null;
  codeAmeli?: string | null;
  codeSirpas?: string | null;
  codeReprographie?: string | null;
  article?: string | null;
  libelle?: string | null;
  libelleCourt?: string | null;
  libelleLong?: string | null;
  ordre?: number | null;
  publicationSurInternet?: string | null;
  typeStructure?: ITypeStructure | null;
  appartenances?: IAppartenance[] | null;
}

export class Structure implements IStructure {
  constructor(
    public id?: number,
    public codeStructure?: string | null,
    public dateDeCreation?: Date | null,
    public dateDeCloture?: Date | null,
    public codeAmeli?: string | null,
    public codeSirpas?: string | null,
    public codeReprographie?: string | null,
    public article?: string | null,
    public libelle?: string | null,
    public libelleCourt?: string | null,
    public libelleLong?: string | null,
    public ordre?: number | null,
    public publicationSurInternet?: string | null,
    public typeStructure?: ITypeStructure | null,
    public appartenances?: IAppartenance[] | null
  ) {}
}
