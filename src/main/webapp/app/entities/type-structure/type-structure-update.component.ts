import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import StructureService from '@/entities/structure/structure.service';
import { IStructure } from '@/shared/model/structure.model';

import { ITypeStructure, TypeStructure } from '@/shared/model/type-structure.model';
import TypeStructureService from './type-structure.service';

const validations: any = {
  typeStructure: {
    codeTypeStructure: {},
    libelle: {},
    libelleCourt: {},
    libellePluriel: {},
    urlComplete: {},
    urlSimplifie: {},
    ordre: {},
  },
};

@Component({
  validations,
})
export default class TypeStructureUpdate extends Vue {
  @Inject('typeStructureService') private typeStructureService: () => TypeStructureService;
  @Inject('alertService') private alertService: () => AlertService;

  public typeStructure: ITypeStructure = new TypeStructure();

  @Inject('structureService') private structureService: () => StructureService;

  public structures: IStructure[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeStructureId) {
        vm.retrieveTypeStructure(to.params.typeStructureId);
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
    if (this.typeStructure.id) {
      this.typeStructureService()
        .update(this.typeStructure)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.typeStructure.updated', { param: param.id });
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
      this.typeStructureService()
        .create(this.typeStructure)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.typeStructure.created', { param: param.id });
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

  public retrieveTypeStructure(typeStructureId): void {
    this.typeStructureService()
      .find(typeStructureId)
      .then(res => {
        this.typeStructure = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.structureService()
      .retrieve()
      .then(res => {
        this.structures = res.data;
      });
  }
}
