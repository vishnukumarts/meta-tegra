SUMMARY = "NVIDIA L4T CUDA binaries"
DESCRIPTION = "Downloads NVIDIA L4T binary-only CUDA packages for sharing with other recipes"
SECTION = "dev"

require cuda-binaries-${PV}.inc

WORKDIR = "${TMPDIR}/work-shared/cuda-binaries-${PV}-${PR}"
SSTATE_SWSPEC = "sstate::cuda-binaries::${PV}:${PR}::${SSTATE_VERSION}:"
STAMP = "${STAMPS_DIR}/work-shared/cuda-binaries-${PV}-${PR}"
STAMPCLEAN = "${STAMPS_DIR}/work-shared/cuda-binaries-${PV}-*"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = ""
PACKAGES = ""

deltask do_configure
deltask do_compile
deltask do_package
deltask do_package_write_rpm
deltask do_package_write_ipk
deltask do_package_write_deb
deltask do_install
deltask do_populate_sysroot
deltask do_package_qa
deltask do_packagedata
deltask do_rm_work

do_preconfigure() {
    dpkg-deb --extract ${S}/var/cuda-repo-7-0-local/cuda-license-7-0_${PV}_armhf.deb ${S}
}
do_preconfigure[depends] += "dpkg-native:do_populate_sysroot"
addtask preconfigure after do_patch

COMPATIBLE_HOST = "(arm.*)"
COMPATIBLE_MACHINE = "(jetson-tx1)"