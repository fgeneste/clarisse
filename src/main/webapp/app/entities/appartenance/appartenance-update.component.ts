import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import TypeAppartenanceService from '@/entities/type-appartenance/type-appartenance.service';
import { ITypeAppartenance } from '@/shared/model/type-appartenance.model';

import StructureService from '@/entities/structure/structure.service';
import { IStructure } from '@/shared/model/structure.model';

import PersonneService from '@/entities/personne/personne.service';
import { IPersonne } from '@/shared/model/personne.model';

import { IAppartenance, Appartenance } from '@/shared/model/appartenance.model';
import AppartenanceService from './appartenance.service';

const validations: any = {
  appartenance: {
    matricule: {},
    dateDebut: {},
    dateFin: {},
    dateElection: {},
    observation: {},
    departement: {},
    libelle: {},
    article: {},
    motifDeDebut: {},
    commentaireDeChangement: {},
  },
};

@Component({
  validations,
})
export default class AppartenanceUpdate extends Vue {
  @Inject('appartenanceService') private appartenanceService: () => AppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public appartenance: IAppartenance = new Appartenance();

  @Inject('typeAppartenanceService') private typeAppartenanceService: () => TypeAppartenanceService;

  public typeAppartenances: ITypeAppartenance[] = [];

  @Inject('structureService') private structureService: () => StructureService;

  public structures: IStructure[] = [];

  @Inject('personneService') private personneService: () => PersonneService;

  public personnes: IPersonne[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.appartenanceId) {
        vm.retrieveAppartenance(to.params.appartenanceId);
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
    if (this.appartenance.id) {
      this.appartenanceService()
        .update(this.appartenance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.appartenance.updated', { param: param.id });
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
      this.appartenanceService()
        .create(this.appartenance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('clarisseApp.appartenance.created', { param: param.id });
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

  public retrieveAppartenance(appartenanceId): void {
    this.appartenanceService()
      .find(appartenanceId)
      .then(res => {
        this.appartenance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.typeAppartenanceService()
      .retrieve()
      .then(res => {
        this.typeAppartenances = res.data;
      });
    this.structureService()
      .retrieve()
      .then(res => {
        this.structures = res.data;
      });
    this.personneService()
      .retrieve()
      .then(res => {
        this.personnes = res.data;
      });
  }
}
