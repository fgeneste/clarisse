import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INoms } from '@/shared/model/noms.model';

import NomsService from './noms.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Noms extends Vue {
  @Inject('nomsService') private nomsService: () => NomsService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public noms: INoms[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNomss();
  }

  public clear(): void {
    this.retrieveAllNomss();
  }

  public retrieveAllNomss(): void {
    this.isFetching = true;
    this.nomsService()
      .retrieve()
      .then(
        res => {
          this.noms = res.data;
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

  public prepareRemove(instance: INoms): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNoms(): void {
    this.nomsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.noms.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllNomss();
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
