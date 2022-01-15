import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import NuancePolitiqueService from '@/entities/nuance-politique/nuance-politique.service';
import { INuancePolitique } from '@/shared/model/nuance-politique.model';

import NomsService from '@/entities/noms/noms.service';
import { INoms } from '@/shared/model/noms.model';

import { IPersonne, Personne } from '@/shared/model/personne.model';
import PersonneService from './personne.service';

const validations: any = {
  personne: {
    matricule: {},
    dateDeNaissance: {},
    lieuDeNaissance: {},
    departementDeNaissance: {},
    dateDeDeces: {},
    lieuDeDeces: {},
    departementDeDeces: {},
    profession: {},
    diplome: {},
    decoration: {},
    rangProtocolaire: {},
    cspInsee: {},
  },
};

@Component({
  validations,
})
export default class PersonneUpdate extends Vue {
  @Inject('personneService') private personneService: () => PersonneService;
  @Inject('alertService') private alertService: () => AlertService;

  public personne: IPersonne = new Personne();

  @Inject('nuancePolitiqueService') private nuancePolitiqueService: () => NuancePolitiqueService;

  public nuancePolitiques: INuancePolitique[] = [];

  @Inject('nomsService') private nomsService: () => NomsService;

  public noms: INoms[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personneId) {
        vm.retrievePersonne(to.params.personneId);
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
    if (this.personne.id) {
      this.personneService()
        .update(this.personne)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.personne.updated', { param: param.id });
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
      this.personneService()
        .create(this.personne)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.personne.created', { param: param.id });
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

  public retrievePersonne(personneId): void {
    this.personneService()
      .find(personneId)
      .then(res => {
        this.personne = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.nuancePolitiqueService()
      .retrieve()
      .then(res => {
        this.nuancePolitiques = res.data;
      });
    this.nomsService()
      .retrieve()
      .then(res => {
        this.noms = res.data;
      });
  }
}
