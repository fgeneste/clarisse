import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import TypeStructureService from '@/entities/type-structure/type-structure.service';
import { ITypeStructure } from '@/shared/model/type-structure.model';

import AppartenanceService from '@/entities/appartenance/appartenance.service';
import { IAppartenance } from '@/shared/model/appartenance.model';

import { IStructure, Structure } from '@/shared/model/structure.model';
import StructureService from './structure.service';

const validations: any = {
  structure: {
    codeStructure: {},
    dateDeCreation: {},
    dateDeCloture: {},
    codeAmeli: {},
    codeSirpas: {},
    codeReprographie: {},
    article: {},
    libelle: {},
    libelleCourt: {},
    libelleLong: {},
    ordre: {},
    publicationSurInternet: {},
  },
};

@Component({
  validations,
})
export default class StructureUpdate extends Vue {
  @Inject('structureService') private structureService: () => StructureService;
  @Inject('alertService') private alertService: () => AlertService;

  public structure: IStructure = new Structure();

  @Inject('typeStructureService') private typeStructureService: () => TypeStructureService;

  public typeStructures: ITypeStructure[] = [];

  @Inject('appartenanceService') private appartenanceService: () => AppartenanceService;

  public appartenances: IAppartenance[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.structureId) {
        vm.retrieveStructure(to.params.structureId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.structure.id) {
      this.structureService()
        .update(this.structure)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.structure.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.structureService()
        .create(this.structure)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.structure.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveStructure(structureId): void {
    this.structureService()
      .find(structureId)
      .then(res => {
        this.structure = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.typeStructureService()
      .retrieve()
      .then(res => {
        this.typeStructures = res.data;
      });
    this.appartenanceService()
      .retrieve()
      .then(res => {
        this.appartenances = res.data;
      });
  }
}
