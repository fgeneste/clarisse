import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStructure } from '@/shared/model/structure.model';
import StructureService from './structure.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StructureDetails extends Vue {
  @Inject('structureService') private structureService: () => StructureService;
  @Inject('alertService') private alertService: () => AlertService;

  public structure: IStructure = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.structureId) {
        vm.retrieveStructure(to.params.structureId);
      }
    });
  }

  public retrieveStructure(structureId) {
    this.structureService()
      .find(structureId)
      .then(res => {
        this.structure = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
