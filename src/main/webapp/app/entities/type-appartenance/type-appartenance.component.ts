import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITypeAppartenance } from '@/shared/model/type-appartenance.model';

import TypeAppartenanceService from './type-appartenance.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TypeAppartenance extends Vue {
  @Inject('typeAppartenanceService') private typeAppartenanceService: () => TypeAppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public typeAppartenances: ITypeAppartenance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTypeAppartenances();
  }

  public clear(): void {
    this.retrieveAllTypeAppartenances();
  }

  public retrieveAllTypeAppartenances(): void {
    this.isFetching = true;
    this.typeAppartenanceService()
      .retrieve()
      .then(
        res => {
          this.typeAppartenances = res.data;
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

  public prepareRemove(instance: ITypeAppartenance): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTypeAppartenance(): void {
    this.typeAppartenanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.typeAppartenance.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTypeAppartenances();
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
