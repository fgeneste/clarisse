import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAppartenance } from '@/shared/model/appartenance.model';

import AppartenanceService from './appartenance.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Appartenance extends Vue {
  @Inject('appartenanceService') private appartenanceService: () => AppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public appartenances: IAppartenance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAppartenances();
  }

  public clear(): void {
    this.retrieveAllAppartenances();
  }

  public retrieveAllAppartenances(): void {
    this.isFetching = true;
    this.appartenanceService()
      .retrieve()
      .then(
        res => {
          this.appartenances = res.data;
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

  public prepareRemove(instance: IAppartenance): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAppartenance(): void {
    this.appartenanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.appartenance.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAppartenances();
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
