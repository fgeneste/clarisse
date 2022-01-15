import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PersonneService from '@/entities/personne/personne.service';
import { IPersonne } from '@/shared/model/personne.model';

import { INuancePolitique, NuancePolitique } from '@/shared/model/nuance-politique.model';
import NuancePolitiqueService from './nuance-politique.service';

const validations: any = {
  nuancePolitique: {
    codeNuancePolitique: {},
    libelle: {},
  },
};

@Component({
  validations,
})
export default class NuancePolitiqueUpdate extends Vue {
  @Inject('nuancePolitiqueService') private nuancePolitiqueService: () => NuancePolitiqueService;
  @Inject('alertService') private alertService: () => AlertService;

  public nuancePolitique: INuancePolitique = new NuancePolitique();

  @Inject('personneService') private personneService: () => PersonneService;

  public personnes: IPersonne[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nuancePolitiqueId) {
        vm.retrieveNuancePolitique(to.params.nuancePolitiqueId);
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
    if (this.nuancePolitique.id) {
      this.nuancePolitiqueService()
        .update(this.nuancePolitique)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.nuancePolitique.updated', { param: param.id });
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
      this.nuancePolitiqueService()
        .create(this.nuancePolitique)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.nuancePolitique.created', { param: param.id });
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

  public retrieveNuancePolitique(nuancePolitiqueId): void {
    this.nuancePolitiqueService()
      .find(nuancePolitiqueId)
      .then(res => {
        this.nuancePolitique = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.personneService()
      .retrieve()
      .then(res => {
        this.personnes = res.data;
      });
  }
}
