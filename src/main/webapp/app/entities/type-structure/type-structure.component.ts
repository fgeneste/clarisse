import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITypeStructure } from '@/shared/model/type-structure.model';

import TypeStructureService from './type-structure.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TypeStructure extends Vue {
  @Inject('typeStructureService') private typeStructureService: () => TypeStructureService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public typeStructures: ITypeStructure[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTypeStructures();
  }

  public clear(): void {
    this.retrieveAllTypeStructures();
  }

  public retrieveAllTypeStructures(): void {
    this.isFetching = true;
    this.typeStructureService()
      .retrieve()
      .then(
        res => {
          this.typeStructures = res.data;
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

  public prepareRemove(instance: ITypeStructure): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTypeStructure(): void {
    this.typeStructureService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('clarisseApp.typeStructure.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTypeStructures();
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
