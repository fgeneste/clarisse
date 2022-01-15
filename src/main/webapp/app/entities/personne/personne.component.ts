import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersonne } from '@/shared/model/personne.model';

import PersonneService from './personne.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Personne extends Vue {
  @Inject('personneService') private personneService: () => PersonneService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public personnes: IPersonne[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersonnes();
  }

  public clear(): void {
    this.retrieveAllPersonnes();
  }

  public retrieveAllPersonnes(): void {
    this.isFetching = true;
    this.personneService()
      .retrieve()
      .then(
        res => {
          this.personnes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPersonne): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersonne(): void {
    this.personneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.personne.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPersonnes();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
