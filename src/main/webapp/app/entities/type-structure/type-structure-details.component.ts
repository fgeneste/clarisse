import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITypeStructure } from '@/shared/model/type-structure.model';
import TypeStructureService from './type-structure.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TypeStructureDetails extends Vue {
  @Inject('typeStructureService') private typeStructureService: () => TypeStructureService;
  @Inject('alertService') private alertService: () => AlertService;

  public typeStructure: ITypeStructure = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeStructureId) {
        vm.retrieveTypeStructure(to.params.typeStructureId);
      }
    });
  }

  public retrieveTypeStructure(typeStructureId) {
    this.typeStructureService()
      .find(typeStructureId)
      .then(res => {
        this.typeStructure = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
