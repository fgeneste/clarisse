import { Component, Vue, Inject } from 'vue-property-decorator';

import { INuancePolitique } from '@/shared/model/nuance-politique.model';
import NuancePolitiqueService from './nuance-politique.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NuancePolitiqueDetails extends Vue {
  @Inject('nuancePolitiqueService') private nuancePolitiqueService: () => NuancePolitiqueService;
  @Inject('alertService') private alertService: () => AlertService;

  public nuancePolitique: INuancePolitique = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nuancePolitiqueId) {
        vm.retrieveNuancePolitique(to.params.nuancePolitiqueId);
      }
    });
  }

  public retrieveNuancePolitique(nuancePolitiqueId) {
    this.nuancePolitiqueService()
      .find(nuancePolitiqueId)
      .then(res => {
        this.nuancePolitique = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
