/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TypeAppartenanceComponent from '@/entities/type-appartenance/type-appartenance.vue';
import TypeAppartenanceClass from '@/entities/type-appartenance/type-appartenance.component';
import TypeAppartenanceService from '@/entities/type-appartenance/type-appartenance.service';
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
  describe('TypeAppartenance Management Component', () => {
    let wrapper: Wrapper<TypeAppartenanceClass>;
    let comp: TypeAppartenanceClass;
    let typeAppartenanceServiceStub: SinonStubbedInstance<TypeAppartenanceService>;

    beforeEach(() => {
      typeAppartenanceServiceStub = sinon.createStubInstance<TypeAppartenanceService>(TypeAppartenanceService);
      typeAppartenanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TypeAppartenanceClass>(TypeAppartenanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          typeAppartenanceService: () => typeAppartenanceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      typeAppartenanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTypeAppartenances();
      await comp.$nextTick();

      // THEN
      expect(typeAppartenanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.typeAppartenances[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      typeAppartenanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(typeAppartenanceServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTypeAppartenance();
      await comp.$nextTick();

      // THEN
      expect(typeAppartenanceServiceStub.delete.called).toBeTruthy();
      expect(typeAppartenanceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
