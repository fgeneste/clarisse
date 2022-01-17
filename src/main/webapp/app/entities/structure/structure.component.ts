import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStructure } from '@/shared/model/structure.model';

import StructureService from './structure.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Structure extends Vue {
  @Inject('structureService') private structureService: () => StructureService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public structures: IStructure[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStructures();
  }

  public clear(): void {
    this.retrieveAllStructures();
  }

  public retrieveAllStructures(): void {
    this.isFetching = true;
    this.structureService()
      .retrieve()
      .then(
        res => {
          this.structures = res.data;
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

  public prepareRemove(instance: IStructure): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStructure(): void {
    this.structureService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.structure.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStructures();
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
