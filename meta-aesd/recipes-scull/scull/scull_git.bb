# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-hgajapathy.git;protocol=ssh;branch=master \
            file://S98lddmodules-scull"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "c8638f184f86f95a5cafbfc14cbdc97b0a3f6248"

S = "${WORKDIR}/git/scull"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE:append_task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules-scull"

FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/scull_load ${D}${bindir}/
    install -m 0755 ${S}/scull_unload ${D}${bindir}/
	install -m 0755 ${WORKDIR}/S98lddmodules-scull ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/scull.ko ${D}/${base_libdir}/modules/${KERNEL_VERSION}/
}
