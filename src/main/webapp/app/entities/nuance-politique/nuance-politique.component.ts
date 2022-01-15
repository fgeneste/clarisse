import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INuancePolitique } from '@/shared/model/nuance-politique.model';

import NuancePolitiqueService from './nuance-politique.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class NuancePolitique extends Vue {
  @Inject('nuancePolitiqueService') private nuancePolitiqueService: () => NuancePolitiqueService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public nuancePolitiques: INuancePolitique[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNuancePolitiques();
  }

  public clear(): void {
    this.retrieveAllNuancePolitiques();
  }

  public retrieveAllNuancePolitiques(): void {
    this.isFetching = true;
    this.nuancePolitiqueService()
      .retrieve()
      .then(
        res => {
          this.nuancePolitiques = res.data;
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

  public prepareRemove(instance: INuancePolitique): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNuancePolitique(): void {
    this.nuancePolitiqueService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.nuancePolitique.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllNuancePolitiques();
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
