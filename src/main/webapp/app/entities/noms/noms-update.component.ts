import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PersonneService from '@/entities/personne/personne.service';
import { IPersonne } from '@/shared/model/personne.model';

import { INoms, Noms } from '@/shared/model/noms.model';
import NomsService from './noms.service';

const validations: any = {
  noms: {
    qualite: {},
    nomUsuel: {},
    prenomUsuel: {},
    nomPatronymique: {},
    prenomCivil: {},
    nomMarital: {},
    nomDistinctif: {},
    nomMajuscule: {},
    nomTechnique: {},
    feminisation: {},
    dateDebut: {},
    dateFin: {},
  },
};

@Component({
  validations,
})
export default class NomsUpdate extends Vue {
  @Inject('nomsService') private nomsService: () => NomsService;
  @Inject('alertService') private alertService: () => AlertService;

  public noms: INoms = new Noms();

  @Inject('personneService') private personneService: () => PersonneService;

  public personnes: IPersonne[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nomsId) {
        vm.retrieveNoms(to.params.nomsId);
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
    if (this.noms.id) {
      this.nomsService()
        .update(this.noms)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.noms.updated', { param: param.id });
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
      this.nomsService()
        .create(this.noms)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.noms.created', { param: param.id });
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

  public retrieveNoms(nomsId): void {
    this.nomsService()
      .find(nomsId)
      .then(res => {
        this.noms = res;
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
