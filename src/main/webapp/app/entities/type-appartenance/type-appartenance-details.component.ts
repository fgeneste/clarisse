import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITypeAppartenance } from '@/shared/model/type-appartenance.model';
import TypeAppartenanceService from './type-appartenance.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TypeAppartenanceDetails extends Vue {
  @Inject('typeAppartenanceService') private typeAppartenanceService: () => TypeAppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public typeAppartenance: ITypeAppartenance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeAppartenanceId) {
        vm.retrieveTypeAppartenance(to.params.typeAppartenanceId);
      }
    });
  }

  public retrieveTypeAppartenance(typeAppartenanceId) {
    this.typeAppartenanceService()
      .find(typeAppartenanceId)
      .then(res => {
        this.typeAppartenance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
