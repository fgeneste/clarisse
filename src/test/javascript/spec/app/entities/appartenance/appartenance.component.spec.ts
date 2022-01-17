/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AppartenanceComponent from '@/entities/appartenance/appartenance.vue';
import AppartenanceClass from '@/entities/appartenance/appartenance.component';
import AppartenanceService from '@/entities/appartenance/appartenance.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Appartenance Management Component', () => {
    let wrapper: Wrapper<AppartenanceClass>;
    let comp: AppartenanceClass;
    let appartenanceServiceStub: SinonStubbedInstance<AppartenanceService>;

    beforeEach(() => {
      appartenanceServiceStub = sinon.createStubInstance<AppartenanceService>(AppartenanceService);
      appartenanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AppartenanceClass>(AppartenanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          appartenanceService: () => appartenanceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      appartenanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAppartenances();
      await comp.$nextTick();

      // THEN
      expect(appartenanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.appartenances[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      appartenanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(appartenanceServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAppartenance();
      await comp.$nextTick();

      // THEN
      expect(appartenanceServiceStub.delete.called).toBeTruthy();
      expect(appartenanceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
