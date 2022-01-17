import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AppartenanceService from '@/entities/appartenance/appartenance.service';
import { IAppartenance } from '@/shared/model/appartenance.model';

import { ITypeAppartenance, TypeAppartenance } from '@/shared/model/type-appartenance.model';
import TypeAppartenanceService from './type-appartenance.service';

const validations: any = {
  typeAppartenance: {
    typeAppartenance: {},
    libelle: {},
  },
};

@Component({
  validations,
})
export default class TypeAppartenanceUpdate extends Vue {
  @Inject('typeAppartenanceService') private typeAppartenanceService: () => TypeAppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public typeAppartenance: ITypeAppartenance = new TypeAppartenance();

  @Inject('appartenanceService') private appartenanceService: () => AppartenanceService;

  public appartenances: IAppartenance[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeAppartenanceId) {
        vm.retrieveTypeAppartenance(to.params.typeAppartenanceId);
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
    if (this.typeAppartenance.id) {
      this.typeAppartenanceService()
        .update(this.typeAppartenance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.typeAppartenance.updated', { param: param.id });
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
      this.typeAppartenanceService()
        .create(this.typeAppartenance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.typeAppartenance.created', { param: param.id });
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

  public retrieveTypeAppartenance(typeAppartenanceId): void {
    this.typeAppartenanceService()
      .find(typeAppartenanceId)
      .then(res => {
        this.typeAppartenance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.appartenanceService()
      .retrieve()
      .then(res => {
        this.appartenances = res.data;
      });
  }
}
