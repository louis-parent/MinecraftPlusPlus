package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class WorldVertexBufferUploader
{
	public void draw(BufferBuilder vertexBufferIn)
	{
		if (vertexBufferIn.getVertexCount() > 0)
		{
			VertexFormat vertexformat = vertexBufferIn.getVertexFormat();
			int i = vertexformat.getNextOffset();
			ByteBuffer bytebuffer = vertexBufferIn.getByteBuffer();
			List<VertexFormatElement> list = vertexformat.getElements();

			for (int j = 0; j < list.size(); ++j)
			{
				VertexFormatElement vertexformatelement = list.get(j);
				VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
				int k = vertexformatelement.getType().getGlConstant();
				int l = vertexformatelement.getIndex();
				((Buffer) bytebuffer).position(vertexformat.getOffset(j));

				switch (vertexformatelement$enumusage)
				{
					case POSITION:
						GlStateManager.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
						GlStateManager.glEnableClientState(32884);
						break;

					case UV:
						OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
						GlStateManager.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
						GlStateManager.glEnableClientState(32888);
						OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
						break;

					case COLOR:
						GlStateManager.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
						GlStateManager.glEnableClientState(32886);
						break;

					case NORMAL:
						GlStateManager.glNormalPointer(k, i, bytebuffer);
						GlStateManager.glEnableClientState(32885);

					default:
						break;
				}
			}

			GlStateManager.glDrawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount());
			int i1 = 0;

			for (int j1 = list.size(); i1 < j1; ++i1)
			{
				VertexFormatElement vertexformatelement1 = list.get(i1);
				VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
				int k1 = vertexformatelement1.getIndex();

				switch (vertexformatelement$enumusage1)
				{
					case POSITION:
						GlStateManager.glDisableClientState(32884);
						break;

					case UV:
						OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + k1);
						GlStateManager.glDisableClientState(32888);
						OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
						break;

					case COLOR:
						GlStateManager.glDisableClientState(32886);
						GlStateManager.resetColor();
						break;

					case NORMAL:
						GlStateManager.glDisableClientState(32885);

					default:
						break;
				}
			}
		}

		vertexBufferIn.reset();
	}
}
